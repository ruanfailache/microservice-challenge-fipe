package com.fipe.infrastructure.adapter.out.persistence.repository.jpa;

import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public interface VehicleRepository extends PanacheRepository<VehicleEntity> {
    
    default List<VehicleEntity> findByBrandCode(String brandCode) {
        return list("brandCode = ?1 order by model", brandCode);
    }
    
    default boolean existsByBrandCodeAndCode(String brandCode, String code) {
        return count("brandCode = ?1 and code = ?2", brandCode, code) > 0;
    }
}
