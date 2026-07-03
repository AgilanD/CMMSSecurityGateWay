package com.example.gateway.usercontext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String gatewayUserId = request.getHeader("X-User-Id");
        String gatewayUsername = request.getHeader("X-User-Name");
        String gatewayRoles = request.getHeader("X-User-Roles");


        if (gatewayUserId != null && !gatewayUserId.isBlank()) {
            UserContext.setUserId(Long.parseLong(gatewayUserId));
        } else {
            UserContext.setUserId(1L);
        }

        if (gatewayUsername != null) UserContext.setUsername(gatewayUsername);
        if (gatewayRoles != null) UserContext.setRoles(gatewayRoles);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
