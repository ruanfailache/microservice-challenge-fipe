package com.fipe.infrastructure.adapter.out.persistence.repository.jpa;

import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;
import com.fipe.infrastructure.adapter.out.persistence.projection.BrandProjection;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VehicleDataRepository implements PanacheRepository<VehicleDataEntity> {
    
    public boolean existsByBrandCodeAndCode(String brandCode, String code) {
        return count("brandCode = ?1 and code = ?2", brandCode, code) > 0;
    }
    
    public Optional<VehicleDataEntity> findByBrandCodeAndCode(String brandCode, String code) {
        return find("brandCode = ?1 and code = ?2", brandCode, code).firstResultOptional();
    }

    public List<VehicleDataEntity> findAllByBrandCode(String brandCode) {
        return list("brandCode", brandCode);
    }

    public Optional<VehicleDataEntity> findBrandByCode(String brandCode) {
        return find("brandCode = ?1", brandCode).firstResultOptional();
    }

    public List<BrandProjection> findDistinctBrands() {
        return getEntityManager()
                .createQuery(
                        "SELECT new com.fipe.infrastructure.adapter.out.persistence.projection.BrandProjection(v.brandCode, v.brandName) " +
                                "FROM VehicleDataEntity v " +
                                "GROUP BY v.brandCode, v.brandName",
                        BrandProjection.class
                )
                .getResultList();
    }
}
