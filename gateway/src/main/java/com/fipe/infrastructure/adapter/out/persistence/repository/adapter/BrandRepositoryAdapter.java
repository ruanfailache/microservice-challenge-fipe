package com.fipe.infrastructure.adapter.out.persistence.repository.adapter;

import com.fipe.domain.model.Brand;
import com.fipe.domain.port.out.repository.BrandRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.mapper.BrandMapper;
import com.fipe.infrastructure.adapter.out.persistence.repository.jpa.BrandRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BrandRepositoryAdapter implements BrandRepositoryPort {
    
    @Inject
    BrandRepository brandRepository;
    
    @Inject
    BrandMapper brandMapper;
    
    @Override
    public List<Brand> findAll() {
        return brandRepository.listAll().stream()
                .map(brandMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Brand> findByCode(String code) {
        return brandRepository.findByCode(code)
                .map(brandMapper::toDomain);
    }
    
    @Override
    @Transactional
    public Brand save(Brand brand) {
        var entity = brandMapper.toEntity(brand);
        brandRepository.persist(entity);
        return brandMapper.toDomain(entity);
    }
    
    @Override
    public boolean existsByCode(String code) {
        return brandRepository.count("code", code) > 0;
    }
}
