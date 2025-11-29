package com.fipe.infrastructure.adapter.in.messaging.consumer;

import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DeadLetterQueueConsumer {
    
    private static final Logger LOG = Logger.getLogger(DeadLetterQueueConsumer.class);
    
    @Inject
    ProcessingFailureRepositoryPort repository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Incoming("vehicle-data-dlq")
    @Transactional
    public CompletionStage<Void> consumeDeadLetter(Message<String> message) {
        try {
            VehicleDataMessage data = objectMapper.readValue(message.getPayload(), VehicleDataMessage.class);
            
            ProcessingFailure failure = createFailure(data);
            enrichWithKafkaMetadata(message, failure, data);
            storeFailure(failure, data);
            
            LOG.errorf("Message failed permanently for brand: %s. Manual intervention required.", data.getBrandCode());
            return message.ack();
            
        } catch (Exception e) {
            LOG.errorf(e, "Failed to process DLQ message: %s", message.getPayload());
            return message.nack(e);
        }
    }
    
    private ProcessingFailure createFailure(VehicleDataMessage data) {
        return new ProcessingFailure(
                data.getBrandCode(),
                data.getBrandName(),
                "Message processing failed after all retries",
                "Failed from DLQ",
                FailureStatus.MANUAL_REVIEW_REQUIRED
        );
    }
    
    private void enrichWithKafkaMetadata(Message<String> message, 
                                        ProcessingFailure failure, VehicleDataMessage data) {
        message.getMetadata(IncomingKafkaRecordMetadata.class).ifPresent(metadata -> {
            LOG.errorf("DLQ - Topic: %s, Partition: %d, Offset: %d, Brand: %s",
                    metadata.getTopic(), metadata.getPartition(), metadata.getOffset(), data.getBrandCode());
            
            failure.setKafkaTopic(metadata.getTopic());
            failure.setKafkaPartition(metadata.getPartition());
            failure.setKafkaOffset(metadata.getOffset());
        });
    }
    
    private void storeFailure(ProcessingFailure failure, VehicleDataMessage data) {
        try {
            repository.save(failure);
            LOG.infof("Stored failure for brand: %s with ID: %d", data.getBrandCode(), failure.getId());
        } catch (Exception e) {
            LOG.errorf(e, "Failed to store failure record for brand: %s", data.getBrandCode());
        }
    }
}
