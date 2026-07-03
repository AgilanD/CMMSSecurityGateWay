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
    private LocalDateTime createdAt;
    private Long createdBy ;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy ;
}

