package com.fipe.infrastructure.adapter.out.rest.dto.response.processor;

import java.time.LocalDateTime;

public record ProcessorVehicleOutResponse(
        Long id,
        String brandCode,
        String brandName,
        String code,
        String model,
        String observations,
        LocalDateTime createdAt
) {
}
