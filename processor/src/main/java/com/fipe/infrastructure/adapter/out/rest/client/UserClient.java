package com.fipe.infrastructure.adapter.out.rest.client;

import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-service")
public interface UserClient {
    
    @POST
    @Path("/api/v1/auth/current-user")
    UserResponse getCurrentUser(@HeaderParam("Authorization") String authorization);
}
