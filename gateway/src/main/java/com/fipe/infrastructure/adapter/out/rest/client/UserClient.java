package com.fipe.infrastructure.adapter.out.rest.client;

import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserClient {
    
    @POST
    @Path("/api/v1/auth/current-user")
    UserResponse getCurrentUser(@HeaderParam("Authorization") String authorization);
}

