package com.fipe.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"brandCode", "code"})
@ToString
public class VehicleData {
    
    private Long id;
    private String brandCode;
    private String brandName;
    private String code;
    private String model;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static VehicleData create(String brandCode, String brandName, String code, String model) {
        VehicleData data = new VehicleData();
        data.setBrandCode(Objects.requireNonNull(brandCode, "Brand code cannot be null"));
        data.setBrandName(Objects.requireNonNull(brandName, "Brand name cannot be null"));
        data.setCode(Objects.requireNonNull(code, "Code cannot be null"));
        data.setModel(Objects.requireNonNull(model, "Model cannot be null"));
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());
        return data;
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
