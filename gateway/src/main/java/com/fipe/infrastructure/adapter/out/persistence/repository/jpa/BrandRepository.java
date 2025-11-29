package com.fipe.infrastructure.adapter.out.persistence.repository.jpa;

import com.fipe.infrastructure.adapter.out.persistence.entity.BrandEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class BrandRepository implements PanacheRepository<BrandEntity> {
    
    public Optional<BrandEntity> findByCode(String code) {
        return find("code", code).firstResultOptional();
    }
}
