package com.example.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {

    private Long id;
    private String customerName;
    private String contactNumber;
    private String email;
    private String address;
    private Boolean isActive;



    private LocalDateTime createdAt;


    private Long createdBy ;


    private LocalDateTime lastModifiedAt;


    private Long lastModifiedBy ;


}
