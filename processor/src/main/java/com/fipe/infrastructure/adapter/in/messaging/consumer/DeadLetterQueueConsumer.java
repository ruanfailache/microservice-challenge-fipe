package com.fipe.infrastructure.adapter.in.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fipe.domain.enums.FailureStatus;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.domain.port.out.repository.ProcessingFailureRepositoryPort;
import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;
import io.smallrye.common.annotation.RunOnVirtualThread;
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
    @RunOnVirtualThread
    @Transactional
    public CompletionStage<Void> consumeDeadLetter(Message<String> message) {
        try {
            VehicleDataMessage data = objectMapper.readValue(message.getPayload(), VehicleDataMessage.class);
            ProcessingFailure failure = createFailure(data);
            enrichWithKafkaMetadata(message, failure);
            storeFailure(failure, data);
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
    
    private void enrichWithKafkaMetadata(Message<String> message, ProcessingFailure failure) {
        message.getMetadata(IncomingKafkaRecordMetadata.class).ifPresent(metadata -> {
            failure.setKafkaTopic(metadata.getTopic());
            failure.setKafkaPartition(metadata.getPartition());
            failure.setKafkaOffset(metadata.getOffset());
        });
    }
    
    private void storeFailure(ProcessingFailure failure, VehicleDataMessage data) {
        repository.save(failure);
    }
}
