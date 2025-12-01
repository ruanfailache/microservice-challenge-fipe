package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.port.in.usecase.InitialLoadUseCase;
import com.fipe.infrastructure.adapter.in.rest.openapi.InitialLoadApi;
import com.fipe.infrastructure.security.annotation.RequiresRole;
import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
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
    @RequiresRole({"ADMIN"})
    public Response triggerInitialLoad(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
        initialLoadUseCase.executeInitialLoad(authorization);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
