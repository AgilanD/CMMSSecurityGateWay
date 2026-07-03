package com.example.gateway.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto {

    @NotBlank(message = "Customer name cannot be empty")
    private String customerName;

    @NotBlank(message = "Contact number cannot be empty")
    @Pattern(
            regexp = "^\\+?\\d{10,15}$",
            message = "Invalid contact number format"
    )
    private String contactNumber;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @Builder.Default
    private Boolean isActive = true;

}

