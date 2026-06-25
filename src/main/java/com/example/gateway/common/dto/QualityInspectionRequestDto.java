package com.example.gateway.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityInspectionRequestDto {

    @NotNull(message = "Production Order ID is required")
    private Long productionOrderId;

    @NotNull(message = "Inspector ID is required")
    private Long inspectorId;

    @NotNull(message = "Inspection result is required")
    private InspectionResult inspectionResult;

    private String remarks;

    public enum InspectionResult {
        PASS, FAIL, PENDING
    }

}
