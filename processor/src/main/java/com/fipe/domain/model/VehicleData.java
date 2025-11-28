package com.fipe.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class VehicleData {
    
    private Long id;
    private final String brandCode;
    private final String brandName;
    private final String code;
    private final String model;
    private final LocalDateTime createdAt;
    
    public VehicleData(Long id, String brandCode, String brandName, String code, String model, LocalDateTime createdAt) {
        this.id = id;
        this.brandCode = Objects.requireNonNull(brandCode, "Brand code cannot be null");
        this.brandName = Objects.requireNonNull(brandName, "Brand name cannot be null");
        this.code = Objects.requireNonNull(code, "Code cannot be null");
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }
    
    public VehicleData(String brandCode, String brandName, String code, String model) {
        this(null, brandCode, brandName, code, model, LocalDateTime.now());
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
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
                ", createdAt=" + createdAt +
                '}';
    }
}
