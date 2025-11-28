package com.fipe.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class VehicleData {
    
    private Long id;
    private final String brandCode;
    private final String brandName;
    private final String code;
    private String model;
    private String observations;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public VehicleData(Long id, String brandCode, String brandName, String code, String model, 
                       String observations, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.brandCode = Objects.requireNonNull(brandCode, "Brand code cannot be null");
        this.brandName = Objects.requireNonNull(brandName, "Brand name cannot be null");
        this.code = Objects.requireNonNull(code, "Code cannot be null");
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.observations = observations;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }
    
    public VehicleData(String brandCode, String brandName, String code, String model) {
        this(null, brandCode, brandName, code, model, null, LocalDateTime.now(), LocalDateTime.now());
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
    
    public String getBrandName() {
        return brandName;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getObservations() {
        return observations;
    }
    
    public void setObservations(String observations) {
        this.observations = observations;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleData that = (VehicleData) o;
        return Objects.equals(brandCode, that.brandCode) && 
               Objects.equals(code, that.code);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(brandCode, code);
    }
    
    @Override
    public String toString() {
        return "VehicleData{" +
                "id=" + id +
                ", brandCode='" + brandCode + '\'' +
                ", brandName='" + brandName + '\'' +
                ", code='" + code + '\'' +
                ", model='" + model + '\'' +
                ", observations='" + observations + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
