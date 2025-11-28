package com.fipe.infrastructure.adapter.out.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FipeModelsWrapper {
    
    @JsonProperty("modelos")
    private List<FipeModelResponse> modelos;
    
    public FipeModelsWrapper() {
    }
    
    public FipeModelsWrapper(List<FipeModelResponse> modelos) {
        this.modelos = modelos;
    }
    
    public List<FipeModelResponse> getModels() {
        return modelos;
    }
    
    public void setModels(List<FipeModelResponse> modelos) {
        this.modelos = modelos;
    }
}
