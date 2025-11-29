package com.fipe.infrastructure.adapter.in.rest.dto.response;

import java.time.LocalDateTime;

public class VehicleResponse {
    
    private Long id;
    private String brandCode;
    private String brandName;
    private String code;
    private String model;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public VehicleResponse() {
    }
    
    public VehicleResponse(Long id, String brandCode, String brandName, String code, String model,
                      String observations, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.code = code;
        this.model = model;
        this.observations = observations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBrandCode() {
        return brandCode;
    }
    
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }
    
    public String getBrandName() {
        return brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
