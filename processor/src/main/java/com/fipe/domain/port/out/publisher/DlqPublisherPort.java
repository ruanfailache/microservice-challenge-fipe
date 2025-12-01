package com.fipe.domain.port.out.publisher;

import com.fipe.infrastructure.adapter.in.messaging.message.VehicleDataMessage;

public interface DlqPublisherPort {
    void sendFallbackDlq(VehicleDataMessage message, String reason);
}

