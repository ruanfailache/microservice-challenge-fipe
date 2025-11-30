package com.fipe.infrastructure.adapter.out.rest.client;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-service")
public interface UserAuthClient {
    
    @POST
    @Path("/api/v1/auth/validate-token")
    Response validateToken(@HeaderParam("Authorization") String authorization);
}