package com.example.gateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.equals("/api/auth/register") || path.equals("/api/auth/login") || path.equals("/api/auth/validate")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtils.getJwtFromCookies(request);
        HttpServletRequest requestToForward = request;

        if (token != null && jwtUtils.validateToken(token)) {
            String username = jwtUtils.getUsernameFromToken(token);
            String role = jwtUtils.getRoleFromToken(token);
            String extractedUserId = jwtUtils.getUserIdFromToken(token);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, token, Collections.singletonList(authority));

            SecurityContextHolder.getContext().setAuthentication(authentication);


            if (RequestContextHolder.getRequestAttributes() != null) {
                RequestContextHolder.currentRequestAttributes().setAttribute(
                        "RAW_JWT_TOKEN",
                        token,
                        RequestAttributes.SCOPE_REQUEST
                );
                RequestContextHolder.currentRequestAttributes().setAttribute(
                        "AUDIT_USER_ID",
                        Long.parseLong(extractedUserId),
                        RequestAttributes.SCOPE_REQUEST
                );
            }


            requestToForward = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if (name == null) {
                        return super.getHeader(null);
                    }

                    switch (name.toLowerCase()) {
                        case "x-user-id", "x-authenticated-user-id" -> {
                            return extractedUserId;
                        }
                        case "x-user-name" -> {
                            return username;
                        }
                        case "x-user-roles" -> {
                            return "ROLE_" + role;
                        }
                        case "authorization" -> {
                            return "Bearer " + token;
                        }
                        default -> {
                            return super.getHeader(name);
                        }
                    }
                }
            };

        }

        filterChain.doFilter(requestToForward, response);
    }
}
