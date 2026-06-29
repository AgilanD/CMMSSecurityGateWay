package com.example.gateway.config;

import com.example.gateway.security.JwtUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
                Long userId = null;

                if (authentication != null) {
                    username = authentication.getName();

                    roles = authentication.getAuthorities().toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "");

                    if (authentication.getCredentials() != null) {
                        token = authentication.getCredentials().toString();
                    }

                    if (authentication.getCredentials() != null) {
                        token = authentication.getCredentials().toString();


                        try {
                            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;

                            userId = Long.parseLong(jwtUtils.getUserIdFromToken(cleanToken));
                        } catch (Exception e) {
                            System.out.println(" Could not extract userId claim from JWT string: " + e.getMessage());
                        }
                    }


                }


                System.out.println("================ FEIGN CONTEXT FIXED ================");
                System.out.println("Forwarding Authorization Token: " + (token != null ? "PRESENT" : "MISSING"));
                System.out.println("Forwarding X-User-Name: " + username);
                System.out.println("Forwarding X-User-Roles: " + roles);
                System.out.println("Forwarding X-User-Id: " + userId);
                System.out.println("=====================================================");

                if (token != null) {
                    String authHeaderValue = token.startsWith("Bearer ") ? token : "Bearer " + token;
                    template.header("Authorization", authHeaderValue);
                }
                if (username != null) template.header("X-User-Name", username);
                if (roles != null) template.header("X-User-Roles", roles);
                if (userId != null) template.header("X-User-Id", String.valueOf(userId));
            }
        };
    }
}
