package org.kt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAnalysisRequestDTO {
    private String sender;
    private String body;
    private List<String> attachments;
}