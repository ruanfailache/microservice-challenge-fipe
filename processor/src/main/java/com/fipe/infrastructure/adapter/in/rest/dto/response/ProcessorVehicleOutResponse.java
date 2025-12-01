package com.fipe.infrastructure.adapter.in.rest.dto.response;

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
