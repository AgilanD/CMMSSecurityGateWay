package com.example.gateway.common.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderRequestDto {

    @NotNull(message = "Plant ID is required")
    private Long plantId;

    @NotNull(message = "Car Model ID is required")
    private Long carModelId;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @NotNull(message = "Target quantity is required")
    @Positive(message = "Target quantity must be greater than zero")
    private Integer targetQuantity;

    private Integer completedQuantity;

    @NotNull(message = "Expected end date is required")
    @FutureOrPresent(message = "Expected end date cannot be in the past")
    private LocalDate expectedEndDate;

    private LocalDate actualEndDate;

    public enum OrderStatus {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }

}
