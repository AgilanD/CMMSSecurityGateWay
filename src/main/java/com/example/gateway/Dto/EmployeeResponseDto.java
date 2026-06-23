package com.example.gateway.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
    private Long id;
    private String employeeCode;
    private String fullName;
    private String designation;
    private LocalDate dateOfBirth;
    private LocalDate joiningDate;
    private String profileImage;
    private Boolean isActive;


    private Long plantId;
    private String plantName;

}

