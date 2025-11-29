package com.fipe.infrastructure.adapter.out.persistence.repository.adapter;

import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.out.repository.VehicleRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleEntity;
import com.fipe.infrastructure.adapter.out.persistence.mapper.VehicleMapper;
import com.fipe.infrastructure.adapter.out.persistence.repository.jpa.VehicleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class VehicleRepositoryAdapter implements VehicleRepositoryPort {
    
    @Inject
    VehicleRepository vehicleRepository;
    
    @Override
    public List<Vehicle> findByBrandCode(String brandCode) {
        return vehicleRepository.findByBrandCode(brandCode).stream()
                .map(VehicleMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findByIdOptional(id)
                .map(VehicleMapper::toDomain);
    }
    
    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity = VehicleMapper.toEntity(vehicle);
        vehicleRepository.persist(entity);
        return VehicleMapper.toDomain(entity);
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByBrandCodeAndCode(String brandCode, String code) {
        return vehicleRepository.existsByBrandCodeAndCode(brandCode, code);
    }
}
