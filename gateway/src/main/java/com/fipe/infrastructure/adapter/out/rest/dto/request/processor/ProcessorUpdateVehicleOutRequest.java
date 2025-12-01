package com.fipe.infrastructure.adapter.out.rest.dto.request.processor;

import com.fipe.infrastructure.adapter.in.rest.dto.request.VehicleUpdateInRequest;

public record ProcessorUpdateVehicleOutRequest(
        String model,
        String observations
) {
    public ProcessorUpdateVehicleOutRequest(VehicleUpdateInRequest request) {
        this(request.getModel(), request.getObservations());
    }
}
