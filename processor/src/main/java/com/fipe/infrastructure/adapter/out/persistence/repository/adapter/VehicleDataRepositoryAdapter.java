package com.fipe.infrastructure.adapter.out.persistence.repository.adapter;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import com.fipe.infrastructure.adapter.out.persistence.entity.VehicleDataEntity;
import com.fipe.infrastructure.adapter.out.persistence.mapper.BrandMapper;
import com.fipe.infrastructure.adapter.out.persistence.mapper.VehicleDataMapper;
import com.fipe.infrastructure.adapter.out.persistence.projection.BrandProjection;
import com.fipe.infrastructure.adapter.out.persistence.repository.jpa.VehicleDataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VehicleDataRepositoryAdapter implements VehicleDataRepositoryPort {
    
    private static final Logger LOG = Logger.getLogger(VehicleDataRepositoryAdapter.class);
    
    @Inject
    VehicleDataRepository vehicleDataRepository;
    
    @Inject
    VehicleDataMapper vehicleDataMapper;
    
    @Inject
    BrandMapper brandMapper;

    @Override
    public VehicleData save(VehicleData vehicleData) {
        LOG.debugf("Saving vehicle data: brand=%s, code=%s", 
                vehicleData.getBrandCode(), vehicleData.getCode());
        
        VehicleDataEntity entity = vehicleDataMapper.toEntity(vehicleData);
        vehicleDataRepository.persist(entity);
        
        LOG.debugf("Vehicle data saved with id: %d", entity.getId());
        return vehicleDataMapper.toDomain(entity);
    }
    
    @Override
    public boolean exists(String brandCode, String code) {
        boolean exists = vehicleDataRepository.existsByBrandCodeAndCode(brandCode, code);
        LOG.debugf("Checking if vehicle data exists: brand=%s, code=%s, exists=%b", brandCode, code, exists);
        return exists;
    }

    @Override
    public List<VehicleData> findAllByBrandCode(String brandCode) {
        LOG.debugf("Finding all vehicles by brand code: %s", brandCode);
        List<VehicleDataEntity> entities = vehicleDataRepository.findAllByBrandCode(brandCode);
        return entities.stream().map(vehicleDataMapper::toDomain).toList();
    }

    @Override
    public Optional<VehicleData> findByBrandCodeAndCode(String brandCode, String code) {
        LOG.debugf("Finding vehicle by brand code and code: brand=%s, code=%s", brandCode, code);
        return vehicleDataRepository.findByBrandCodeAndCode(brandCode, code).map(vehicleDataMapper::toDomain);
    }

    @Override
    public VehicleData update(VehicleData vehicleData) {
        LOG.debugf("Updating vehicle data: id=%d", vehicleData.getId());
        VehicleDataEntity entity = vehicleDataMapper.toEntity(vehicleData);
        vehicleDataRepository.getEntityManager().merge(entity);
        return vehicleDataMapper.toDomain(entity);
    }

    @Override
    public Optional<VehicleData> findBrandByCode(String brandCode) {
        LOG.debugf("Finding brand by code: %s", brandCode);
        return vehicleDataRepository.findBrandByCode(brandCode).map(vehicleDataMapper::toDomain);
    }

    @Override
    public List<Brand> findDistinctBrands() {
        LOG.debug("Finding distinct brands");
        List<BrandProjection> projections = vehicleDataRepository.findDistinctBrands();
        return projections.stream().map(brandMapper::toBrand).toList();
    }
}
