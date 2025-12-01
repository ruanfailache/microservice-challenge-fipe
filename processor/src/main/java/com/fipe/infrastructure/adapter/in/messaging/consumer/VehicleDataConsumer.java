package com.fipe.infrastructure.adapter.in.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fipe.domain.port.in.usecase.ProcessVehicleDataUseCase;
import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class VehicleDataConsumer {
    
    private static final Logger LOG = Logger.getLogger(VehicleDataConsumer.class);
    
    @Inject
    ProcessVehicleDataUseCase useCase;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Incoming("vehicle-data-in")
    @RunOnVirtualThread
    @Retry(maxRetries = 3, delay = 5, delayUnit = ChronoUnit.SECONDS, jitter = 1000)
    public CompletionStage<Void> consume(Message<String> message) {
        try {
            VehicleDataMessage data = objectMapper.readValue(message.getPayload(), VehicleDataMessage.class);
            useCase.processVehicleData(data.getBrandCode(), data.getBrandName());
            return message.ack();
        } catch (Exception e) {
            LOG.errorf(e, "Failed processing message: %s", message.getPayload());
            return message.nack(e);
        }
    }
}
