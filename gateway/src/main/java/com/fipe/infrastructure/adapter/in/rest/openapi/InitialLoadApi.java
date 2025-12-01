package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorInResponse;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Initial Load", description = "Operations for initial vehicle data load")
public interface InitialLoadApi {
    
    @Operation(summary = "Trigger initial load", description = "Fetches all vehicle brands from FIPE and publishes them for processing")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "202",
                    description = "Initial load triggered successfully"
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorInResponse.class))
            )
    })
    Response triggerInitialLoad(String authorization);
}
