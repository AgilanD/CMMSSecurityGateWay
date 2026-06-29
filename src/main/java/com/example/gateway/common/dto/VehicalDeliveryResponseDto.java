package com.example.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicalDeliveryResponseDto {

    private Long id;
    private String invoiceNumber;
    private Long vehicleId;
    private Long customerId;
    private LocalDate deliveryDate;
    private BigDecimal invoiceAmount;
    private Long deliveredById;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

}
