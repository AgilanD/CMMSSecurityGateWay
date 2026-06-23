package com.example.gateway.Dto;


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
public class CarModuleResponseDto {

    private Long id;
    private String modelName;
    private FuelType fuelType;
    private Transmission transmission;
    private BigDecimal basePrice;
    private String colorOptions;
    private LocalDate launchDate;
    private Boolean isActive;


    public enum FuelType {
        PETROL, DIESEL, ELECTRIC, HYBRID
    }

    public enum Transmission {
        MANUAL, AUTOMATIC
    }
}
