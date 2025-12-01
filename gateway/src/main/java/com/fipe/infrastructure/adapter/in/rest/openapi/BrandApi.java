package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.request.VehicleUpdateInRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandInResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorInResponse;
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

@Tag(name = "Brands", description = "Brand management operations")
public interface BrandApi {
    
    @Operation(summary = "Get all brands", description = "Retrieves all vehicle brands from the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Brands retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = BrandInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getAllBrands(String authorization);

    @Operation(summary = "Get brand by code", description = "Retrieves a brand by its code")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Brand retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = BrandInResponse.class))),
            @APIResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getBrandByCode(
            @Parameter(description = "Authorization header", required = true) String authorization,
            @Parameter(description = "Brand code", required = true) String code);

    @Operation(summary = "Get vehicles by brand", description = "Retrieves all vehicles for a specific brand including their observations")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VehicleInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getVehiclesByBrand(
            @Parameter(description = "Brand code", required = true) String brandCode,
            @Parameter(description = "Authorization header", required = true) String authorization);

    @Operation(summary = "Update vehicle", description = "Updates vehicle model and observations")
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
            @Parameter(description = "Authorization header", required = true) String authorization,
            @Parameter(description = "Vehicle ID", required = true) Long id,
            VehicleUpdateInRequest updateDTO);
}
