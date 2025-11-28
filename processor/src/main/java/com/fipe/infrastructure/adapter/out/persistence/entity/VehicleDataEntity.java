package com.fipe.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vehicle_data", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_code", "code"})
})
public class VehicleDataEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "brand_code", nullable = false, length = 50)
    private String brandCode;
    
    @Column(name = "brand_name", nullable = false, length = 255)
    private String brandName;
    
    @Column(name = "code", nullable = false, length = 50)
    private String code;
    
    @Column(name = "model", nullable = false, length = 500)
    private String model;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDataEntity that = (VehicleDataEntity) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
