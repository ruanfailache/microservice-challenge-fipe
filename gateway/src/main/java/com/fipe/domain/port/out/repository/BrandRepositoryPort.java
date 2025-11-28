package com.fipe.domain.port.out.repository;

import com.fipe.domain.model.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandRepositoryPort {
    
    List<Brand> findAll();
    
    Optional<Brand> findByCode(String code);
    
    Brand save(Brand brand);
    
    boolean existsByCode(String code);
}
