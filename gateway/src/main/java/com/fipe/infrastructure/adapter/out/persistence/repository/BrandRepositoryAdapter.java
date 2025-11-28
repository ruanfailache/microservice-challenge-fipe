package com.fipe.infrastructure.adapter.out.persistence.repository;

import com.fipe.domain.model.Brand;
import com.fipe.domain.port.out.repository.BrandRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.entity.BrandEntity;
import com.fipe.infrastructure.adapter.out.persistence.mapper.BrandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BrandRepositoryAdapter implements BrandRepositoryPort {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public List<Brand> findAll() {
        return entityManager.createQuery("SELECT b FROM BrandEntity b ORDER BY b.name", BrandEntity.class)
                .getResultList()
                .stream()
                .map(BrandMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Brand> findByCode(String code) {
        try {
            BrandEntity entity = entityManager.createQuery(
                    "SELECT b FROM BrandEntity b WHERE b.code = :code", BrandEntity.class)
                    .setParameter("code", code)
                    .getSingleResult();
            return Optional.of(BrandMapper.toDomain(entity));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    @Override
    @Transactional
    public Brand save(Brand brand) {
        BrandEntity entity = BrandMapper.toEntity(brand);
        
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        
        return BrandMapper.toDomain(entity);
    }
    
    @Override
    public boolean existsByCode(String code) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(b) FROM BrandEntity b WHERE b.code = :code", Long.class)
                .setParameter("code", code)
                .getSingleResult();
        return count > 0;
    }
}
