package com.fipe.infrastructure.adapter.in.rest.dto;

public class UpdateVehicleDTO {
    
    private String model;
    private String observations;
    
    public UpdateVehicleDTO() {
    }
    
    public UpdateVehicleDTO(String model, String observations) {
        this.model = model;
        this.observations = observations;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getObservations() {
        return observations;
    }
    
    public void setObservations(String observations) {
        this.observations = observations;
    }
}
