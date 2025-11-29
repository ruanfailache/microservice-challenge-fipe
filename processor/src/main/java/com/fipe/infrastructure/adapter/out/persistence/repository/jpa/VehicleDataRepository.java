package com.fipe.infrastructure.adapter.out.persistence.repository.jpa;

import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class VehicleDataRepository implements PanacheRepository<VehicleDataEntity> {
    
    public boolean existsByBrandCodeAndCode(String brandCode, String code) {
        return count("brandCode = ?1 and code = ?2", brandCode, code) > 0;
    }
    
    public Optional<VehicleDataEntity> findByBrandCodeAndCode(String brandCode, String code) {
        return find("brandCode = ?1 and code = ?2", brandCode, code).firstResultOptional();
    }
}
