package com.fipe.infrastructure.adapter.out.persistence.repository;

import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.out.repository.VehicleRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleEntity;
import com.fipe.infrastructure.adapter.out.persistence.mapper.VehicleMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class VehicleRepositoryAdapter implements VehicleRepositoryPort {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public List<Vehicle> findByBrandCode(String brandCode) {
        return entityManager.createQuery(
                "SELECT v FROM VehicleEntity v WHERE v.brandCode = :brandCode ORDER BY v.model", 
                VehicleEntity.class)
                .setParameter("brandCode", brandCode)
                .getResultList()
                .stream()
                .map(VehicleMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Vehicle> findById(Long id) {
        VehicleEntity entity = entityManager.find(VehicleEntity.class, id);
        return entity != null ? Optional.of(VehicleMapper.toDomain(entity)) : Optional.empty();
    }
    
    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity = VehicleMapper.toEntity(vehicle);
        
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        
        return VehicleMapper.toDomain(entity);
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
        VehicleEntity entity = entityManager.find(VehicleEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
    
    @Override
    public boolean existsByBrandCodeAndCode(String brandCode, String code) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(v) FROM VehicleEntity v WHERE v.brandCode = :brandCode AND v.code = :code", 
                Long.class)
                .setParameter("brandCode", brandCode)
                .setParameter("code", code)
                .getSingleResult();
        return count > 0;
    }
}
