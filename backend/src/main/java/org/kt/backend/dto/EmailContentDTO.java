package org.kt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailContentDTO {
    private Integer emailNo;
    private String content;

    public EmailContentDTO(String content) {
        this.content = content;
    }

}
