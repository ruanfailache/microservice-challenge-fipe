package com.fipe.infrastructure.adapter.out.persistence.adapter;

import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;
import com.fipe.infrastructure.adapter.out.persistence.mapper.VehicleDataMapper;
import com.fipe.infrastructure.adapter.out.persistence.repository.VehicleDataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class VehicleDataRepositoryAdapter implements VehicleDataRepositoryPort {
    
    private static final Logger LOG = Logger.getLogger(VehicleDataRepositoryAdapter.class);
    
    @Inject
    VehicleDataRepository vehicleDataRepository;
    
    @Override
    public VehicleData save(VehicleData vehicleData) {
        LOG.debugf("Saving vehicle data: brand=%s, code=%s", 
                vehicleData.getBrandCode(), vehicleData.getCode());
        
        VehicleDataEntity entity = VehicleDataMapper.toEntity(vehicleData);
        vehicleDataRepository.persist(entity);
        
        LOG.debugf("Vehicle data saved with id: %d", entity.getId());
        return VehicleDataMapper.toDomain(entity);
    }
    
    @Override
    public boolean exists(String brandCode, String code) {
        boolean exists = vehicleDataRepository.existsByBrandCodeAndCode(brandCode, code);
        LOG.debugf("Checking if vehicle data exists: brand=%s, code=%s, exists=%b", 
                brandCode, code, exists);
        return exists;
    }
}
