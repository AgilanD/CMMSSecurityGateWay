////package com.example.gateway.security;
////
////import jakarta.servlet.FilterChain;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.authority.SimpleGrantedAuthority;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.stereotype.Component;
////import org.springframework.web.filter.OncePerRequestFilter;
////import java.io.IOException;
////import java.util.Collections;
////
////@Component
////public class JwtAuthenticationFilter extends OncePerRequestFilter {
////
////    private final JwtUtils jwtUtils;
////
////    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
////        this.jwtUtils = jwtUtils;
////    }
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////            throws ServletException, IOException {
////
////        String token = jwtUtils.getJwtFromCookies(request);
////
////        if (token != null && jwtUtils.validateToken(token)) {
////            String username = jwtUtils.getUsernameFromToken(token);
////            String role = jwtUtils.getRoleFromToken(token);
////
////            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
////            UsernamePasswordAuthenticationToken authentication =
////                    new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
////
////            SecurityContextHolder.getContext().setAuthentication(authentication);
////        }
////        filterChain.doFilter(request, response);
////    }
////}
//
//
//package com.example.gateway.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//import java.util.Collections;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtils jwtUtils;
//
//    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
//        this.jwtUtils = jwtUtils;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String path = request.getRequestURI();
//
//        // CRITICAL FIX: If the path is a public endpoint, skip this filter completely
//        if (path.equals("/api/auth/register") || path.equals("/api/auth/login") || path.equals("/api/auth/validate")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = jwtUtils.getJwtFromCookies(request);
//
//        if (token != null && jwtUtils.validateToken(token)) {
//            String username = jwtUtils.getUsernameFromToken(token);
//            String role = jwtUtils.getRoleFromToken(token);
//
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}



package com.example.gateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequestWrapper; // Needed to add headers
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
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

            Long userId = jwtUtils.getUserIdFromToken(token);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            requestToForward = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if ("X-Authenticated-User-Id".equalsIgnoreCase(name)) {
                        log.info("<==============>"+name);
                        return String.valueOf(userId);
                    }
                    return super.getHeader(name);
                }
            };
        }

        filterChain.doFilter(requestToForward, response);
    }
}

