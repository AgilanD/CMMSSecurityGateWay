package com.example.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationsResponseDto {

    private Long id;
    private RecipientRole recipientRole;
    private String message;
    private NotificationType notificationType;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

    public enum RecipientRole {
        ADMIN, PLANT_MANAGER, SUPERVISOR
    }

    public enum NotificationType {
        QC_FAIL, DELIVERY, STATUS_CHANGE
    }

}
