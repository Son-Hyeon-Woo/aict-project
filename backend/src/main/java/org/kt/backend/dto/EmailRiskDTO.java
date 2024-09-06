package org.kt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRiskDTO {
    private Integer emailNo;
    private String riskLevel;
    private String riskDetail;
    private LocalDateTime detectionDate;
    // private String detectionResult;

    // Getters and Setters
}
