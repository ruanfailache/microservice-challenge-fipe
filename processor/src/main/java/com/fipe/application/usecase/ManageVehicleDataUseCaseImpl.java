package com.fipe.application.usecase;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.in.usecase.ManageVehicleDataUseCase;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ManageVehicleDataUseCaseImpl implements ManageVehicleDataUseCase {

    private static final Logger LOG = Logger.getLogger(ManageVehicleDataUseCaseImpl.class);

    @Inject
    VehicleDataRepositoryPort vehicleDataRepositoryPort;

    public List<Brand> getAllBrands() {
        LOG.debug("Getting all brands");
        return vehicleDataRepositoryPort.findDistinctBrands();
    }

    public Optional<VehicleData> getBrandByCode(String brandCode) {
        LOG.debugf("Getting brand by code: %s", brandCode);
        return vehicleDataRepositoryPort.findBrandByCode(brandCode);
    }

    public List<VehicleData> getVehiclesByBrand(String brandCode) {
        LOG.debugf("Getting vehicles by brand: %s", brandCode);
        return vehicleDataRepositoryPort.findAllByBrandCode(brandCode);
    }

    public Optional<VehicleData> getVehicleById(Long vehicleId) {
        return vehicleDataRepositoryPort.findById(vehicleId);
    }

    @Transactional
    public VehicleData updateVehicle(VehicleData vehicleData) {
        LOG.debugf("Updating vehicle: id=%d", vehicleData.getId());
        return vehicleDataRepositoryPort.update(vehicleData);
    }
}
