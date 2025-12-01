package com.fipe.infrastructure.adapter.out.rest.dto.request.processor;

public record ProcessorUpdateVehicleOutRequest(
        String model,
        String observations
) {}
