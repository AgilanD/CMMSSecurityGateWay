package com.example.gateway.aspect;

import com.example.gateway.clients.UserFeignClientLogistics;
import com.example.gateway.common.dto.NotificationsRequestDto;
import com.example.gateway.common.dto.QualityInspectionRequestDto;
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
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationAspect {

    private final UserFeignClientLogistics userFeignClientLogistics;
    private final ObjectMapper objectMapper;

    @AfterReturning(value = "@annotation(trackNotification)", returning = "result")
    public void processWorkflowNotification(JoinPoint joinPoint, TrackNotification trackNotification, Object result) {
        NotificationsRequestDto.NotificationType type = trackNotification.type();

        if (type == NotificationsRequestDto.NotificationType.QC_FAIL && !isActualQcFail(joinPoint)) {
            return;
        }

        HttpServletRequest request = getServletRequest();
        Long currentUserId = extractUserId(request);
        String headerRole = (request != null) ? request.getHeader("X-User-Role") : null;

        log.info("Notification AOP Triggered: Mapping metadata for virtual target: {}", trackNotification.tableName());

        NotificationsRequestDto.RecipientRole dynamicRole = resolveRecipientRole(joinPoint, headerRole);
        Long recordId = extractRecordId(joinPoint, result);
        String operatorName = fetchOperatorName(currentUserId);
        String systemMessage = generateSystemMessage(type, operatorName, recordId);

        NotificationsRequestDto notificationDto = NotificationsRequestDto.builder()
                .recipientRole(dynamicRole)
                .message(systemMessage)
                .notificationType(type)
                .isRead(false)
                .build();

        userFeignClientLogistics.createNotifications(notificationDto);
        log.info("Notification payload successfully transmitted directly via Feign Client.");
    }

    private boolean isActualQcFail(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .filter(QualityInspectionRequestDto.class::isInstance)
                .map(QualityInspectionRequestDto.class::cast)
                .anyMatch(dto -> dto.getInspectionResult() == QualityInspectionRequestDto.InspectionResult.FAIL);
    }

    private HttpServletRequest getServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (attributes != null) ? attributes.getRequest() : null;
    }

    private Long extractUserId(HttpServletRequest request) {
        if (request == null) return 0L;
        String userIdHeader = request.getHeader("X-User-Id");
        if (userIdHeader == null || userIdHeader.isBlank()) return 0L;
        try {
            return Long.parseLong(userIdHeader);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse X-User-Id header: {}", userIdHeader);
            return 0L;
        }
    }

    private NotificationsRequestDto.RecipientRole resolveRecipientRole(JoinPoint joinPoint, String headerRole) {
        String resolvedRoleStr = extractRoleFromArgs(joinPoint.getArgs());

        if (resolvedRoleStr == null && headerRole != null && !headerRole.isBlank()) {
            resolvedRoleStr = headerRole;
            log.info("Dynamic Role resolved from HTTP Header context: {}", resolvedRoleStr);
        }

        if (resolvedRoleStr == null) {
            resolvedRoleStr = "ADMIN";
            log.warn("Dynamic Role could not be parsed. Defaulting safely to: {}", resolvedRoleStr);
        }

        try {
            return NotificationsRequestDto.RecipientRole.valueOf(resolvedRoleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid enum value mapping string target '{}'. Fallback defaults used instead.", resolvedRoleStr, e);
            return NotificationsRequestDto.RecipientRole.SUPERVISOR;
        }
    }

    private String fetchOperatorName(Long currentUserId) {
        if (currentUserId == 0L) return "Unknown Operator";
        try {
            var userResponse = userFeignClientLogistics.getByIdVehical(currentUserId);
            if (userResponse != null) {
                var jsonNode = objectMapper.valueToTree(userResponse);
                if (jsonNode.has("name")) {
                    return jsonNode.get("name").asText();
                }
            }
        } catch (Exception feignException) {
            log.error("Feign client notification failure fetching user metadata info for ID: {}", currentUserId, feignException);
        }
        return "Unknown Operator";
    }

    private String extractRoleFromArgs(Object[] args) {
        if (args == null) return null;
        for (Object arg : args) {
            if (arg == null) continue;
            String resolvedRole = invokeRoleGetter(arg);
            if (resolvedRole != null) {
                log.info("Dynamic Role resolved from method argument payload: {}", resolvedRole);
                return resolvedRole;
            }
        }
        return null;
    }

    private String invokeRoleGetter(Object arg) {
        try {
            Method targetMethod = Arrays.stream(arg.getClass().getMethods())
                    .filter(m -> "getRecipientRole".equals(m.getName()) || "getRole".equals(m.getName()))
                    .findFirst()
                    .orElse(null);

            if (targetMethod != null) {
                Object roleObj = targetMethod.invoke(arg);
                return (roleObj != null) ? roleObj.toString() : null;
            }
        } catch (Exception e) {
            log.debug("Failed to extract role via reflection from object type: {}", arg.getClass().getName());
        }
        return null;
    }

    private Long extractRecordId(JoinPoint joinPoint, Object result) {
        Long id = extractIdFromWithResult(result);
        if (id != null) {
            return id;
        }
        return extractIdFromArgs(joinPoint.getArgs());
    }

    private Long extractIdFromWithResult(Object result) {
        if (result == null) return null;
        if (result instanceof ResponseEntity<?> responseEntity) {
            result = responseEntity.getBody();
        }
        if (result == null) return null;
        try {
            Method getIdMethod = result.getClass().getMethod("getId");
            Object idObj = getIdMethod.invoke(result);
            if (idObj instanceof Long longId) {
                return longId;
            }
        } catch (Exception e) {
            log.debug("Could not extract record ID via reflection: {}", e.getMessage());
        }
        return null;
    }

    private Long extractIdFromArgs(Object[] args) {
        if (args == null) return null;
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