package com.fipe.infrastructure.adapter.out.rest.dto.response.fipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FipeBrandOutResponse {
    
    @JsonProperty("codigo")
    private String code;
    
    @JsonProperty("nome")
    private String name;
}
