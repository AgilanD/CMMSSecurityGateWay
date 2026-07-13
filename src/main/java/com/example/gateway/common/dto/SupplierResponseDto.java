package com.example.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponseDto {

    private Long id;
    private String supplierName;
    private String supplierCode;
    private String contactEmail;
    private Integer rating;
    private String gstNumber;
    private Boolean isActive;

}
