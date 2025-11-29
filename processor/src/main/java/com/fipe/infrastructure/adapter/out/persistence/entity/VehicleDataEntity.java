package com.fipe.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_data", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_code", "code"})
})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
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
    
    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
