package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Brands", description = "Brand management operations")
public interface BrandApi {
    
    @Operation(summary = "Get all brands", description = "Retrieves all vehicle brands from the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Brands retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = BrandResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    Response getAllBrands();
    
    @Operation(summary = "Get brand by code", description = "Retrieves a specific brand by its code")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Brand retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = BrandResponse.class))),
            @APIResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    Response getBrandByCode(String code);
}
