package org.kt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAttachmentDTO {
    private Integer attachmentNo;
    private String fileName;
    private String filePath;
    private Integer fileSize;

    public EmailAttachmentDTO(String fileName, String filePath, Integer fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
