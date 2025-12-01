package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.request.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorInResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorBrandOutResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorVehicleOutResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Brand", description = "Operations for managing processed vehicle's brand data")
public interface BrandApi {

    @Operation(summary = "Get all brands",
               description = "Retrieve all processed brands")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "List of brands",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessorBrandOutResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getAllBrands();

    @Operation(summary = "Get brand by code",
               description = "Retrieve a specific brand by its code")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Brand found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessorBrandOutResponse.class))),
            @APIResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getBrandByCode(
            @Parameter(description = "Brand code", required = true) String brandCode);

    @Operation(summary = "Get vehicles by brand",
               description = "Retrieve all vehicles for a specific brand")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "List of vehicles",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessorVehicleOutResponse.class))),
            @APIResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getVehiclesByBrand(
            @Parameter(description = "Brand code", required = true) String brandCode);
}
