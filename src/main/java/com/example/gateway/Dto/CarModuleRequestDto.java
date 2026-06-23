package com.example.gateway.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarModuleRequestDto {

    @NotBlank(message = "Model name is mandatory and cannot be blank")
    private String modelName;

    @NotNull(message = "Fuel type is mandatory")
    private FuelType fuelType;

    @NotNull(message = "Transmission type is mandatory")
    private Transmission transmission;

    @NotNull(message = "Base price is mandatory")
    @Positive(message = "Base price must be greater than zero")
    private BigDecimal basePrice;

    private String colorOptions;

    private LocalDate launchDate;

    @Builder.Default
    private Boolean isActive = true;

    public enum FuelType {
        PETROL, DIESEL, ELECTRIC, HYBRID
    }

    public enum Transmission {
        MANUAL, AUTOMATIC
    }

}


