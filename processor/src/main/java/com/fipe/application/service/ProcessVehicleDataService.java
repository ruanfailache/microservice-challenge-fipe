package com.fipe.application.service;

import com.fipe.domain.model.Model;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.in.usecase.ProcessVehicleDataUseCase;
import com.fipe.domain.port.out.client.FipeClientPort;
import com.fipe.domain.port.out.repository.VehicleDataRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class ProcessVehicleDataService implements ProcessVehicleDataUseCase {
    
    private static final Logger LOG = Logger.getLogger(ProcessVehicleDataService.class);
    
    @Inject
    FipeClientPort fipeClientPort;
    
    @Inject
    VehicleDataRepositoryPort vehicleDataRepositoryPort;
    
    @Override
    @Transactional
    public void processVehicleData(String brandCode, String brandName) {
        LOG.infof("Starting to process vehicle data for brand: %s - %s", brandCode, brandName);
        
        // Fetch all models for the brand from external API (throws ExternalServiceException on failure)
        List<Model> models = fipeClientPort.fetchModelsByBrand(brandCode);
        
        if (models.isEmpty()) {
            LOG.warnf("No models found for brand: %s", brandCode);
            return;
        }
        
        LOG.infof("Found %d models for brand: %s", models.size(), brandCode);
        
        // Save each model to the database
        int savedCount = 0;
        int skippedCount = 0;
        
        for (Model model : models) {
            try {
                // Check if already exists to avoid duplicates
                if (vehicleDataRepositoryPort.exists(brandCode, model.getCode())) {
                    LOG.debugf("Vehicle data already exists for brand: %s, code: %s. Skipping.", 
                            brandCode, model.getCode());
                    skippedCount++;
                    continue;
                }
                
                VehicleData vehicleData = new VehicleData(
                        brandCode,
                        brandName,
                        model.getCode(),
                        model.getName()
                );
                
                vehicleDataRepositoryPort.save(vehicleData);
                savedCount++;
                
            } catch (Exception e) {
                LOG.errorf(e, "Failed to save vehicle data for brand: %s, model: %s", 
                        brandCode, model.getCode());
                // Continue processing other models
            }
        }
        
        LOG.infof("Completed processing for brand: %s. Saved: %d, Skipped: %d", 
                brandCode, savedCount, skippedCount);
    }
}
