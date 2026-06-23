package com.example.gateway.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantsResponseDto {

    private Long id;
    private String name;
    private String code;
    private String location;
    private Integer capacityPerDay;
    private Boolean isActive;

}
