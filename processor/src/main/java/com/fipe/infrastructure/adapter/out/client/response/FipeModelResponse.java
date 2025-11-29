package com.fipe.infrastructure.adapter.out.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FipeModelResponse {
    
    @JsonProperty("codigo")
    private String codigo;
    
    @JsonProperty("nome")
    private String nome;
    
    public String getCode() {
        return codigo;
    }
    
    public String getName() {
        return nome;
    }
}
