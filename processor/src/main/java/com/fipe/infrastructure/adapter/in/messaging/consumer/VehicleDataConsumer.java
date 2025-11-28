package com.fipe.infrastructure.adapter.in.messaging.consumer;

import com.fipe.domain.port.in.usecase.ProcessVehicleDataUseCase;
import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class VehicleDataConsumer {
    
    private static final Logger LOG = Logger.getLogger(VehicleDataConsumer.class);
    
    @Inject
    ProcessVehicleDataUseCase processVehicleDataUseCase;
    
    @Incoming("vehicle-data-in")
    public void consume(VehicleDataMessage message) {
        try {
            LOG.infof("Received message to process vehicle data for brand: %s - %s", 
                    message.getBrandCode(), message.getBrandName());
            
            processVehicleDataUseCase.processVehicleData(
                    message.getBrandCode(),
                    message.getBrandName()
            );
            
            LOG.infof("Successfully processed vehicle data for brand: %s", message.getBrandCode());
            
        } catch (Exception e) {
            LOG.errorf(e, "Error processing vehicle data for brand: %s", message.getBrandCode());
            // In a production system, you might want to implement a dead letter queue or retry mechanism
            throw e;
        }
    }
}
