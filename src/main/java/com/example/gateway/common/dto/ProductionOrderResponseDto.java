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
public class ProductionOrderResponseDto {

    private Long id;
    private String orderNumber;
    private Long plantId;
    private Long carModelId;
    private OrderStatus status;
    private Integer targetQuantity;
    private Integer completedQuantity;
    private LocalDate expectedEndDate;
    private LocalDate actualEndDate;


    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;


    public enum OrderStatus {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }

}
