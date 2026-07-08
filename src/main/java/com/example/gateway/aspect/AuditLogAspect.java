package com.example.gateway.aspect;

import com.example.gateway.clients.UserFeignClientSystem;
import com.example.gateway.common.dto.AuditLogsRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditLogAspect {

    private final UserFeignClientSystem userFeignClientSystem;
    private final ObjectMapper objectMapper;

    @AfterReturning(value = "@annotation(auditLoggable)", returning = "result")
    public void logAudit(JoinPoint joinPoint, AuditLoggable auditLoggable, Object result) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return;

            HttpServletRequest request = attributes.getRequest();
            String ipAddress = request.getRemoteAddr();
            String userIdHeader = request.getHeader("X-User-Id");
            Long performedById = (userIdHeader != null) ? Long.parseLong(userIdHeader) : 0L;

            Long recordId = extractRecordId(joinPoint, result);

            String changedData = "";
            if (result != null) {
                changedData = objectMapper.writeValueAsString(result);
            } else if (joinPoint.getArgs().length > 0) {
                changedData = objectMapper.writeValueAsString(joinPoint.getArgs());
            }

            AuditLogsRequestDto auditLog = AuditLogsRequestDto.builder()
                    .tableName(auditLoggable.tableName())
                    .action(auditLoggable.action())
                    .recordId(recordId)
                    .changedData(changedData)
                    .performedById(performedById)
                    .ipAddress(ipAddress)
                    .build();

            userFeignClientSystem.createAuditLog(auditLog);
            log.info("Audit log successfully sent directly via Feign Client.");

        } catch (Exception e) {
            log.error("Failed to process audit log in gateway: ", e);
        }
    }

    private Long extractRecordId(JoinPoint joinPoint, Object result) {
        if (result != null) {
            try {
                if (result instanceof ResponseEntity<?> responseEntity) {
                    result = responseEntity.getBody();
                }
                if (result != null) {
                    Method getIdMethod = result.getClass().getMethod("getId");
                    Object id = getIdMethod.invoke(result);
                    if (id instanceof Long longId) {
                        return longId;
                    }
                }
            }
            catch (Exception e) {
                log.info("Error Message"+e.getMessage());
            }
        }

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Long longArg) {
                return longArg;
            }
        }
        return null;
    }
}