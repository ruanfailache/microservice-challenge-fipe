package com.fipe.infrastructure.adapter.out.messaging.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fipe.domain.port.out.publisher.DlqPublisherPort;
import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class KafkaFallbackDlqPublisher implements DlqPublisherPort {

    private static final Logger LOG = Logger.getLogger(KafkaFallbackDlqPublisher.class);

    @Channel("fallback-dlq-out")
    Emitter<String> emitter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendFallbackDlq(VehicleDataMessage message, String reason) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("brandCode", message.getBrandCode());
            payload.put("brandName", message.getBrandName());
            payload.put("reason", reason);
            payload.put("timestamp", Instant.now().toString());

            String json = objectMapper.writeValueAsString(payload);

            OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String>builder()
                    .withKey(message.getBrandCode())
                    .build();

            emitter.send(Message.of(json).addMetadata(metadata));
            LOG.warnf("Sent fallback DLQ message for brand %s", message.getBrandCode());
        } catch (Exception e) {
            LOG.errorf(e, "Failed to send fallback DLQ message for brand %s", message != null ? message.getBrandCode() : "<null>");
        }
    }
}

