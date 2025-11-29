package com.fipe.infrastructure.adapter.in.messaging.consumer;

import com.fipe.domain.port.in.usecase.ProcessVehicleDataUseCase;
import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
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
    
    @Incoming("vehicle-data-in")
    @Retry(maxRetries = 3, delay = 5, delayUnit = ChronoUnit.SECONDS, jitter = 1000)
    public CompletionStage<Void> consume(Message<VehicleDataMessage> message) {
        VehicleDataMessage data = message.getPayload();
        logMessageReceived(message, data);
        
        try {
            useCase.processVehicleData(data.getBrandCode(), data.getBrandName());
            LOG.infof("Processed brand: %s", data.getBrandCode());
            return message.ack();
            
        } catch (Exception e) {
            LOG.errorf(e, "Failed processing brand: %s", data.getBrandCode());
            return message.nack(e);
        }
    }
    
    private void logMessageReceived(Message<VehicleDataMessage> message, VehicleDataMessage data) {
        message.getMetadata(IncomingKafkaRecordMetadata.class).ifPresent(metadata ->
            LOG.infof("Received - Topic: %s, Partition: %d, Offset: %d, Brand: %s",
                    metadata.getTopic(), metadata.getPartition(), metadata.getOffset(), data.getBrandCode())
        );
    }
}
