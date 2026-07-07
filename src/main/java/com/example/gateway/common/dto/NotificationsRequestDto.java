package com.example.gateway.common.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationsRequestDto {


    @NotNull(message = "Recipient role is required")
    private RecipientRole recipientRole;


    @NotBlank(message = "Notification message is required")
    private String message;


    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;


    @Builder.Default
    private Boolean isRead = false;


    public enum RecipientRole {
        ADMIN, PLANT_MANAGER, SUPERVISOR
    }

    public enum NotificationType {
        QC_FAIL, DELIVERY, STATUS_CHANGE
    }
}
