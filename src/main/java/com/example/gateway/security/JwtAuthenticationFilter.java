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
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
                        ServletRequestAttributes.SCOPE_REQUEST
                );
            }


            requestToForward = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if ("X-User-Id".equalsIgnoreCase(name) || "X-Authenticated-User-Id".equalsIgnoreCase(name)) {
                        return extractedUserId;
                    }
                    if ("X-User-Name".equalsIgnoreCase(name)) {
                        return username;
                    }
                    if ("X-User-Roles".equalsIgnoreCase(name)) {
                        return "ROLE_" + role;
                    }
                    if ("Authorization".equalsIgnoreCase(name)) {
                        return "Bearer " + token;
                    }
                    return super.getHeader(name);
                }
            };
        }

        filterChain.doFilter(requestToForward, response);
    }
}
