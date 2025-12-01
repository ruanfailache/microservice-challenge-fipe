package com.fipe.infrastructure.adapter.in.rest.dto.request;

public record ProcessorUpdateVehicleOutRequest(
        String code,
        String model,
        String observations
) {}
