package org.kt.backend.dto;

import java.time.LocalDateTime;
import org.kt.backend.entity.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockedEmailDTO {

    private String title;
    private String riskLevel;
    private String riskDetail;
    private LocalDateTime detectionDate;

    public BlockedEmailDTO(Email email) {
        this.title = email.getSubject();
        this.riskLevel = email.getRiskLevel().getRiskLevel();
        this.riskDetail = email.getRiskLevel().getRiskDetail();
        this.detectionDate = email.getRiskLevel().getDetectionDate();
    }

}