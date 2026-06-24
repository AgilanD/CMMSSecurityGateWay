package com.example.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogsRequestDto {

    private String tableName;

    private Long recordId;

    private AuditAction action;

    private String changedData;

    private Long performedById;

    private String ipAddress;

    public enum AuditAction {
        CREATE,
        UPDATE,
        DELETE,
        LOGIN_SUCCESS,
        LOGIN_FAILURE
    }

}
