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
public class VehicleInventoryRequestDto {

    @NotNull(message = "Production Order ID is required")
    private Long productionOrderId;

    @NotNull(message = "Car Model ID is required")
    private Long carModelId;

    @NotBlank(message = "Vehicle color is required")
    private String color;

    @NotNull(message = "Vehicle status is required")
    private VehicleStatus status;

    public enum VehicleStatus {
        MANUFACTURED, INSPECTED, DELIVERED
    }

}
