package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.in.usecase.VehicleQueryUseCase;
import com.fipe.domain.port.in.usecase.VehicleUpdateUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.VehicleUpdateRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.VehicleResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.VehicleMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.VehicleApi;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleController implements VehicleApi {
    
    @Inject
    VehicleQueryUseCase vehicleQueryUseCase;
    
    @Inject
    VehicleUpdateUseCase vehicleUpdateUseCase;
    
    @GET
    @Path("/brand/{brandCode}")
    @PermitAll
    @Override
    public Response getVehiclesByBrand(@PathParam("brandCode") String brandCode) {
        
        List<Vehicle> vehicles = vehicleQueryUseCase.getVehiclesByBrandCode(brandCode);
        List<VehicleResponse> dtos = vehicles.stream()
                .map(VehicleMapper::toDTO)
                .collect(Collectors.toList());
        
        return Response.ok(dtos).build();
    }
    
    @GET
    @Path("/{id}")
    @PermitAll
    @Override
    public Response getVehicleById(@PathParam("id") Long id) {
        Vehicle vehicle = vehicleQueryUseCase.getVehicleById(id);
        VehicleResponse dto = VehicleMapper.toDTO(vehicle);
        return Response.ok(dto).build();
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public Response updateVehicle(@PathParam("id") Long id, VehicleUpdateRequest updateDTO) {
        Vehicle updated = vehicleUpdateUseCase.updateVehicle(
                id, 
                updateDTO.getModel(), 
                updateDTO.getObservations()
        );
        VehicleResponse dto = VehicleMapper.toDTO(updated);
        return Response.ok(dto).build();
    }
}
