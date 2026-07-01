package com.example.gateway.config;

import com.example.gateway.security.JwtUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@RequiredArgsConstructor
public class FeignClientInterceptorConfig {

    private final JwtUtils jwtUtils;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                String token = null;
                String username = null;
                String roles = null;
                String userId = null;

                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    token = (String) attributes.getAttribute("RAW_JWT_TOKEN", ServletRequestAttributes.SCOPE_REQUEST);
                }


                if (authentication != null) {
                    username = authentication.getName();

                    roles = authentication.getAuthorities().toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "");


                    if (token != null) {
                        try {
                            userId = jwtUtils.getUserIdFromToken(token);
                        } catch (Exception e) {

                        }
                    }

                    if (userId == null || userId.isBlank()) {
                        userId = username;
                    }
                }


                if (token != null) {
                    template.header("Authorization", "Bearer " + token);
                }
                if (username != null) template.header("X-User-Name", username);
                if (roles != null) template.header("X-User-Roles", roles);
                if (userId != null) template.header("X-User-Id", userId);
            }
        };
    }
}

