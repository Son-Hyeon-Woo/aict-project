package org.kt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRecipientDTO {
    private Integer recipientNo;
    private String recipientEmail;
    private String recipientType;

    public EmailRecipientDTO(String recipientEmail, String recipientType) {
        this.recipientEmail = recipientEmail;
        this.recipientType = recipientType;
    }
}
