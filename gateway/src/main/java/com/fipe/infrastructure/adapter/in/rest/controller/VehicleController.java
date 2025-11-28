package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.application.service.VehicleQueryService;
import com.fipe.application.service.VehicleUpdateService;
import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.in.rest.dto.UpdateVehicleDTO;
import com.fipe.infrastructure.adapter.in.rest.dto.VehicleDTO;
import com.fipe.infrastructure.adapter.in.rest.mapper.VehicleDTOMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Vehicles", description = "Vehicle management operations")
public class VehicleController {
    
    @Inject
    VehicleQueryService vehicleQueryService;
    
    @Inject
    VehicleUpdateService vehicleUpdateService;
    
    @GET
    @Path("/brand/{brandCode}")
    @PermitAll
    @Operation(summary = "Get vehicles by brand", 
               description = "Retrieves all vehicles for a specific brand including their observations")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VehicleDTO.class))),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getVehiclesByBrand(
            @Parameter(description = "Brand code", required = true)
            @PathParam("brandCode") String brandCode) {
        
        List<Vehicle> vehicles = vehicleQueryService.getVehiclesByBrandCode(brandCode);
        List<VehicleDTO> dtos = vehicles.stream()
                .map(VehicleDTOMapper::toDTO)
                .collect(Collectors.toList());
        
        return Response.ok(dtos).build();
    }
    
    @GET
    @Path("/{id}")
    @PermitAll
    @Operation(summary = "Get vehicle by ID", description = "Retrieves a specific vehicle by its ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicle retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VehicleDTO.class))),
            @APIResponse(responseCode = "404", description = "Vehicle not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getVehicleById(
            @Parameter(description = "Vehicle ID", required = true)
            @PathParam("id") Long id) {
        Vehicle vehicle = vehicleQueryService.getVehicleById(id);
        VehicleDTO dto = VehicleDTOMapper.toDTO(vehicle);
        return Response.ok(dto).build();
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    @Operation(summary = "Update vehicle", 
               description = "Updates vehicle model and observations")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicle updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VehicleDTO.class))),
            @APIResponse(responseCode = "404", description = "Vehicle not found"),
            @APIResponse(responseCode = "400", description = "Invalid input"),
            @APIResponse(responseCode = "401", description = "Unauthorized"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response updateVehicle(
            @Parameter(description = "Vehicle ID", required = true)
            @PathParam("id") Long id,
            UpdateVehicleDTO updateDTO) {
        Vehicle updated = vehicleUpdateService.updateVehicle(
                id, 
                updateDTO.getModel(), 
                updateDTO.getObservations()
        );
        VehicleDTO dto = VehicleDTOMapper.toDTO(updated);
        return Response.ok(dto).build();
    }
}
