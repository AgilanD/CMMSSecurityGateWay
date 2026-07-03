package com.example.gateway.config;

import com.example.gateway.security.JwtUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Configuration
@RequiredArgsConstructor
public class FeignClientInterceptorConfig  {

    private final JwtUtils jwtUtils;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String token = extractToken();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                String username = authentication.getName();
                String roles = cleanAuthorities(authentication);
                String userId = resolveUserId(token, username);

                addHeaderIfValid(template, "X-User-Name", username);
                addHeaderIfValid(template, "X-User-Roles", roles);
                addHeaderIfValid(template, "X-User-Id", userId);
            }

            if (token != null && !token.isBlank()) {
                template.header("Authorization", "Bearer " + token);
            }
        };
    }

    private String extractToken() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return (String) attributes.getAttribute("RAW_JWT_TOKEN", RequestAttributes.SCOPE_REQUEST);
        }
        return null;
    }

    private String cleanAuthorities(Authentication auth) {
        return auth.getAuthorities().toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "");
    }

    private String resolveUserId(String token, String fallbackUsername) {
        if (token != null && !token.isBlank()) {
            String extractedId = jwtUtils.getUserIdFromToken(token);
            if (extractedId != null && !extractedId.isBlank()) {
                return extractedId;
            }
        }
        return fallbackUsername;
    }

    private void addHeaderIfValid(RequestTemplate template, String headerName, String value) {
        if (value != null && !value.isBlank()) {
            template.header(headerName, value);
        }
    }
}

