package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.exception.InitialLoadException;
import com.fipe.domain.port.in.usecase.InitialLoadUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.ErrorResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.InitialLoadResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

@Path("/api/v1/initial-load")
@Tag(name = "Initial Load", description = "Operations for initial vehicle data load")
public class InitialLoadController {
    
    private static final Logger LOG = Logger.getLogger(InitialLoadController.class);
    
    @Inject
    InitialLoadUseCase initialLoadUseCase;
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    @Operation(summary = "Trigger initial load", description = "Fetches all vehicle brands from FIPE and publishes them for processing")
    @APIResponse(
            responseCode = "202",
            description = "Initial load triggered successfully",
            content = @Content(schema = @Schema(implementation = InitialLoadResponse.class))
    )
    @APIResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public Response triggerInitialLoad() {
        try {
            LOG.info("Received request to trigger initial load");
            
            int brandsProcessed = initialLoadUseCase.executeInitialLoad();
            
            InitialLoadResponse response = new InitialLoadResponse(
                    "Initial load triggered successfully",
                    brandsProcessed,
                    "PROCESSING"
            );
            
            LOG.infof("Initial load triggered successfully. Brands processed: %d", brandsProcessed);
            return Response.status(Response.Status.ACCEPTED).entity(response).build();
            
        } catch (InitialLoadException e) {
            LOG.error("Error triggering initial load", e);
            ErrorResponse errorResponse = new ErrorResponse(
                    "Failed to trigger initial load",
                    e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }
}
