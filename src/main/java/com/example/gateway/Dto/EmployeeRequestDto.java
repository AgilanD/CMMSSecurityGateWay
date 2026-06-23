package com.example.gateway.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestDto {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Plant ID assignment is required")
    private Long plantId;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Past(message = "Joining date cannot be a future date")
    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;


}
