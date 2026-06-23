package com.example.gateway.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantsRequestDto {

    @NotBlank(message = "Plant name is mandatory and cannot be empty")
    private String name;

    @NotBlank(message = "Plant code is mandatory and cannot be empty")
    private String code;

    @NotBlank(message = "Plant location is mandatory and cannot be empty")
    private String location;

    @NotNull(message = "Capacity per day is mandatory")
    @PositiveOrZero(message = "Capacity per day must be zero or a positive number")
    private Integer capacityPerDay;

    @Builder.Default
    private Boolean isActive = true;
}
