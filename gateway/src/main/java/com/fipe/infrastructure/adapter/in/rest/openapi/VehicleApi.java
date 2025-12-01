package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorInResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.request.VehicleUpdateRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.VehicleInResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Vehicles", description = "Vehicle management operations")
public interface VehicleApi {
    
    @Operation(summary = "Get vehicles by brand", 
               description = "Retrieves all vehicles for a specific brand including their observations")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VehicleInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getVehiclesByBrand(
            @Parameter(description = "Brand code", required = true) String brandCode);
    
    @Operation(summary = "Get vehicle by ID", description = "Retrieves a specific vehicle by its ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicle retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VehicleInResponse.class))),
            @APIResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getVehicleById(
            @Parameter(description = "Vehicle ID", required = true) Long id);
    
    @Operation(summary = "Update vehicle", 
               description = "Updates vehicle model and observations")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicle updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VehicleInResponse.class))),
            @APIResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response updateVehicle(
            @Parameter(description = "Vehicle ID", required = true) Long id,
            VehicleUpdateRequest updateDTO);
}
