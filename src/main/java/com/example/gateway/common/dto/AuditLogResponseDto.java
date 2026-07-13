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
public class AuditLogResponseDto {

    private Long id;
    private String tableName;
    private Long recordId;
    private AuditAction action;
    private String changedData;
    private Long performedById;
    private String performedByUsername;
    private String ipAddress;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

    public enum AuditAction {
        CREATE,
        UPDATE,
        DELETE,
        LOGIN_SUCCESS,
        LOGIN_FAILURE
    }
}
