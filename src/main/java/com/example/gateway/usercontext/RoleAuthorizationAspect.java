package com.example.gateway.usercontext;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAuthorizationAspect {

    @Before("@annotation(requireRole)")
    public void authorizeRole(RequireRole requireRole) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Request Context Error");
        }

        HttpServletRequest request = attributes.getRequest();

        String forwardedRoles = request.getHeader("X-User-Roles");
        if (forwardedRoles == null || forwardedRoles.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied: Missing role authorization headers.");
        }

        List<String> userRoles = Arrays.stream(forwardedRoles.split(","))
                .map(role -> role.trim().toUpperCase().replace("ROLE_", ""))
                .toList();

        boolean hasPermission = Arrays.stream(requireRole.value())
                .map(String::toUpperCase)
                .anyMatch(userRoles::contains);

        if (!hasPermission) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden: Insufficient role privileges.");
        }
    }
}
