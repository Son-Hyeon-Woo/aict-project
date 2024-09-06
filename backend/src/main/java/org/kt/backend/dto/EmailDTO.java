package org.kt.backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private Integer emailNo;
    private String messageNo;
    private String sender;
    private String subject;
    private LocalDateTime receivedDate;
    private String processStatus;
    private String processResult;
    private List<EmailRecipientDTO> recipients;
    private EmailContentDTO content; 
    private List<EmailAttachmentDTO> attachments;
    private EmailRiskDTO riskLevel;

}
