package com.example.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleInventoryResponseDto {
    private Long id;
    private String vin;
    private Long productionOrderId;
    private Long carModelId;
    private String color;
    private VehicleStatus status;
    private LocalDate manufacturedDate;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

    public enum VehicleStatus {
        MANUFACTURED, INSPECTED, DELIVERED
    }

}
