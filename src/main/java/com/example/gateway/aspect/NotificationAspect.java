package com.example.gateway.aspect;

import com.example.gateway.clients.UserFeignClientLogistics;
import com.example.gateway.common.dto.NotificationsRequestDto;
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
public class NotificationAspect {

    private final UserFeignClientLogistics userFeignClientLogistics;
    private final ObjectMapper objectMapper;

    @AfterReturning(value = "@annotation(trackNotification)", returning = "result")
    public void processWorkflowNotification(JoinPoint joinPoint, TrackNotification trackNotification, Object result) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return;

            HttpServletRequest request = attributes.getRequest();
            String userIdHeader = request.getHeader("X-User-Id");
            Long currentUserId = (userIdHeader != null) ? Long.parseLong(userIdHeader) : 0L;

            NotificationsRequestDto.NotificationType type = trackNotification.type();
            String tableName = trackNotification.tableName();

            log.info("Notification AOP Triggered: Mapping metadata for virtual target: {}", tableName);

            String resolvedRoleStr = null;

            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg != null) {
                    try {
                        Method getRoleMethod = null;
                        try {
                            getRoleMethod = arg.getClass().getMethod("getRecipientRole");
                        } catch (NoSuchMethodException e) {
                            getRoleMethod = arg.getClass().getMethod("getRole");
                        }

                        Object roleObj = getRoleMethod.invoke(arg);
                        if (roleObj != null) {
                            resolvedRoleStr = roleObj.toString();
                            log.info("Dynamic Role resolved from method argument payload: {}", resolvedRoleStr);
                            break;
                        }
                    } catch (Exception e) {

                    }
                }
            }

            if (resolvedRoleStr == null) {
                String roleHeader = request.getHeader("X-User-Role");
                if (roleHeader != null && !roleHeader.isBlank()) {
                    resolvedRoleStr = roleHeader;
                    log.info("Dynamic Role resolved from HTTP Header context: {}", resolvedRoleStr);
                }
            }

            if (resolvedRoleStr == null) {
                resolvedRoleStr = "ADMIN";
                log.warn("Dynamic Role could not be parsed. Defaulting safely to: {}", resolvedRoleStr);
            }

            NotificationsRequestDto.RecipientRole dynamicRole;
            try {
                dynamicRole = NotificationsRequestDto.RecipientRole.valueOf(resolvedRoleStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.error("Invalid enum value mapping string target. Fallback defaults used instead.", e);
                dynamicRole = NotificationsRequestDto.RecipientRole.SUPERVISOR;
            }

            Long recordId = extractRecordId(joinPoint, result);

            String operatorName = "Unknown Operator";
            try {
                var userResponse = userFeignClientLogistics.getByIdVehical(currentUserId);
                if (userResponse != null) {
                    var jsonNode = objectMapper.valueToTree(userResponse);
                    if (jsonNode.has("name")) {
                        operatorName = jsonNode.get("name").asText();
                    }
                }
            } catch (Exception feignException) {
                log.error("Feign client notification failure fetching user metadata info: {}", currentUserId, feignException);
            }

            String systemMessage = generateSystemMessage(type, operatorName, recordId);

            NotificationsRequestDto notificationDto = NotificationsRequestDto.builder()
                    .recipientRole(dynamicRole)
                    .message(systemMessage)
                    .notificationType(type)
                    .isRead(false)
                    .build();

            userFeignClientLogistics.createNotifications(notificationDto);
            log.info("Notification payload successfully transmitted directly via Feign Client [/AddNotification].");

        } catch (Exception e) {
            log.error("Failed to process notification pipeline handling in gateway aspect: ", e);
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
            } catch (Exception e) {
                log.debug("Could not extract record ID via reflection: " + e.getMessage());
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

    private String generateSystemMessage(NotificationsRequestDto.NotificationType type, String operatorName, Long recordId) {
        String idSuffix = (recordId != null) ? " (ID: " + recordId + ")" : "";
        return switch (type) {
            case QC_FAIL -> String.format("System Alert: A quality control inspection has registered a status of FAIL%s. Action executed by %s.", idSuffix, operatorName);
            case DELIVERY -> String.format("Logistics Alert: A new delivery dispatch record has been registered successfully%s by %s.", idSuffix, operatorName);
            case STATUS_CHANGE -> String.format("Operational Alert: Production order workflow status modification executed%s by %s.", idSuffix, operatorName);
        };
    }
}