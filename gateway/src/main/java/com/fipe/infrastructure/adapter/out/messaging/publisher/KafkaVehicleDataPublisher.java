package com.fipe.infrastructure.adapter.out.messaging.publisher;

import com.fipe.domain.exception.MessagingException;
import com.fipe.domain.model.Brand;
import com.fipe.domain.port.out.publisher.VehicleDataPublisherPort;
import com.fipe.infrastructure.adapter.out.messaging.message.VehicleDataMessage;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaVehicleDataPublisher implements VehicleDataPublisherPort {
    
    private static final Logger LOG = Logger.getLogger(KafkaVehicleDataPublisher.class);
    
    @Channel("vehicle-data-out")
    Emitter<VehicleDataMessage> emitter;
    
    @Override
    public void publishBrandForProcessing(Brand brand) {
        try {
            VehicleDataMessage message = new VehicleDataMessage(
                    brand.getCode(),
                    brand.getName()
            );
            
            // Add Kafka headers with message key
            OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String>builder()
                    .withKey(brand.getCode())
                    .build();
            
            emitter.send(Message.of(message).addMetadata(metadata));
            
            LOG.infof("Published brand for processing: %s - %s", brand.getCode(), brand.getName());
            
        } catch (Exception e) {
            LOG.errorf(e, "Error publishing brand for processing: %s", brand.getCode());
            throw new MessagingException("Failed to publish brand for processing", e);
        }
    }
}
