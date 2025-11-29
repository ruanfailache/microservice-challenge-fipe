package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.port.in.usecase.InitialLoadUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.InitialLoadResponse;
import com.fipe.infrastructure.adapter.in.rest.openapi.InitialLoadApi;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/api/v1/initial-load")
public class InitialLoadController implements InitialLoadApi {
    
    private static final Logger LOG = Logger.getLogger(InitialLoadController.class);
    
    @Inject
    InitialLoadUseCase initialLoadUseCase;
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    @Override
    public Response triggerInitialLoad() {
        LOG.info("Received request to trigger initial load");
        
        int brandsProcessed = initialLoadUseCase.executeInitialLoad();
        
        InitialLoadResponse response = new InitialLoadResponse(
                "Initial load triggered successfully",
                brandsProcessed,
                "PROCESSING"
        );
        
        LOG.infof("Initial load triggered successfully. Brands processed: %d", brandsProcessed);
        return Response.status(Response.Status.ACCEPTED).entity(response).build();
    }
}
