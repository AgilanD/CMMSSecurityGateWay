package com.example.gateway.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class VehicalDeliveryRequestDto {

    @NotBlank(message = "Invoice number is required")
    private String invoiceNumber;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @PastOrPresent(message = "Delivery date cannot be in the future")
    private LocalDate deliveryDate;

    @NotNull(message = "Invoice amount is required")
    private BigDecimal invoiceAmount;

    @NotNull(message = "Delivered by employee ID is required")
    private Long deliveredById;

}
