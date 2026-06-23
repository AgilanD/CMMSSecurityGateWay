package com.example.gateway.common;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierRequestDto {

    @NotBlank(message = "Supplier name is mandatory and cannot be empty")
    private String supplierName;

    @NotBlank(message = "Supplier code is mandatory and cannot be empty")
    private String supplierCode;

    @NotBlank(message = "Contact email is mandatory")
    @Email(message = "Invalid email format")
    private String contactEmail;

    @NotNull(message = "Rating is mandatory")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;

    @Pattern(
            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
            message = "Invalid 15-character Indian GSTIN format"
    )
    private String gstNumber;

    @Builder.Default
    private Boolean isActive = true;
}
