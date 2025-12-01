package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.in.usecase.ManageVehicleDataUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorVehicleOutResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.ProcessorRestMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.VehicleApi;
import com.fipe.infrastructure.security.annotation.RequiresRole;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/api/v1/vehicle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleController implements VehicleApi {

    @Inject
    ManageVehicleDataUseCase manageVehicleDataUseCase;

    @Inject
    ProcessorRestMapper processorRestMapper;

    @PUT
    @Path("/{vehicleId}")
    @RequiresRole({"ADMIN"})
    public Response updateVehicle(@PathParam("vehicleId") Long vehicleId, ProcessorUpdateVehicleOutRequest request) {
        Optional<VehicleData> vehicleData = manageVehicleDataUseCase.getVehicleById(vehicleId);
        if (vehicleData.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        processorRestMapper.updateVehicleDataFromRequest(vehicleData.get(), request);
        VehicleData updated = manageVehicleDataUseCase.updateVehicle(vehicleData.get());
        ProcessorVehicleOutResponse response = processorRestMapper.toVehicleResponse(updated);
        return Response.ok(response).build();
    }
}
