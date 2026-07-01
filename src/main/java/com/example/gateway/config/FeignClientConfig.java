package com.example.gateway.config;

import com.example.gateway.security.JwtUtils;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    private final JwtUtils jwtUtils;

    public FeignClientConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    public RequestInterceptor feignCookieRelayInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest currentBrowserRequest = attributes.getRequest();
                String tokenString = jwtUtils.getJwtFromCookies(currentBrowserRequest);
                if (tokenString != null) {
                    template.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString);
                }
            }
        };
    }
}
