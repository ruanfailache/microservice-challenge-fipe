package com.fipe.infrastructure.adapter.out.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FipeBrandResponse {
    
    @JsonProperty("codigo")
    private String code;
    
    @JsonProperty("nome")
    private String name;
}
