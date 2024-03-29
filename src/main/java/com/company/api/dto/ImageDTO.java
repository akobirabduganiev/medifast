package com.company.api.dto;

import com.company.api.entity.PatientEntity;
import com.company.api.enums.ImageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ImageDTO {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String link;
    private ImageType type;
    private PatientEntity patient;
}
