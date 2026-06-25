package com.example.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceHistoryResponseDto {

    private Long id;
    private Long vehicleId;
    private LocalDate serviceDate;
    private String serviceCenter;
    private ServiceType serviceType;
    private BigDecimal cost;
    private String remarks;


    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

    public enum ServiceType {
        ROUTINE, WARRANTY, REPAIR, RECALL
    }

}
