package com.company.api.dto.authorization;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AuthResponseDTO {
    private String name;
    private String surname;
    private String username;
    private String jwt;
}
