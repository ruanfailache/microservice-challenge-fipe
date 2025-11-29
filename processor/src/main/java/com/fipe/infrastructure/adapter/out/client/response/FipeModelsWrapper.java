package com.fipe.infrastructure.adapter.out.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FipeModelsWrapper {
    
    @JsonProperty("modelos")
    private List<FipeModelResponse> models;
}
