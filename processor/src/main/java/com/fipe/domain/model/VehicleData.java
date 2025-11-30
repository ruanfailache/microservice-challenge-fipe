package com.fipe.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(of = {"brandCode", "code"})
@ToString
public class VehicleData {
    
    private Long id;
    private String brandCode;
    private String brandName;
    private String code;
    private String model;
    private String observations;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public VehicleData(Long id, String brandCode, String brandName, String code, String model, String observations, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.brandCode = Objects.requireNonNull(brandCode, "Brand code cannot be null");
        this.brandName = Objects.requireNonNull(brandName, "Brand name cannot be null");
        this.code = Objects.requireNonNull(code, "Code cannot be null");
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.observations = observations;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }
    
    public void setModel(String model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setObservations(String observations) {
        this.observations = observations;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setBrandCode(String brandCode) {
        this.brandCode = Objects.requireNonNull(brandCode, "Brand code cannot be null");
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setBrandName(String brandName) {
        this.brandName = Objects.requireNonNull(brandName, "Brand name cannot be null");
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setCode(String code) {
        this.code = Objects.requireNonNull(code, "Code cannot be null");
        this.updatedAt = LocalDateTime.now();
    }
}
