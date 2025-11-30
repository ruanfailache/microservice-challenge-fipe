package com.fipe.infrastructure.adapter.out.rest.client;

import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.out.rest.request.UserAuthenticationRequest;
import com.fipe.infrastructure.adapter.out.rest.response.UserServiceResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * REST client for User Service
 * Handles both authentication and user management operations
 */
@RegisterRestClient(configKey = "user-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserClient {
    
    /**
     * Validate JWT token
     */
    @POST
    @Path("/api/v1/auth/validate-token")
    UserResponse validateToken(@HeaderParam("Authorization") String authorization);
    
    /**
     * Get user by username
     */
    @GET
    @Path("/api/users/username/{username}")
    UserServiceResponse getUserByUsername(@PathParam("username") String username);
    
    /**
     * Get user by ID
     */
    @GET
    @Path("/api/users/{id}")
    UserServiceResponse getUserById(@PathParam("id") Long id);
}

