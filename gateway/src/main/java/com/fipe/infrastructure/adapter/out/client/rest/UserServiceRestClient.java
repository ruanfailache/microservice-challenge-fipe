package com.fipe.infrastructure.adapter.out.client.rest;

import com.fipe.infrastructure.adapter.out.rest.request.UserAuthenticationRequest;
import com.fipe.infrastructure.adapter.out.rest.response.UserServiceResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * REST client for User Service
 */
@Path("/api/users")
@RegisterRestClient(configKey = "user-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserServiceRestClient {
    
    /**
     * Validate user credentials
     */
    @POST
    @Path("/validate")
    UserServiceResponse validateCredentials(UserAuthenticationRequest request);
    
    /**
     * Get user by username
     */
    @GET
    @Path("/username/{username}")
    UserServiceResponse getUserByUsername(@PathParam("username") String username);
    
    /**
     * Get user by ID
     */
    @GET
    @Path("/{id}")
    UserServiceResponse getUserById(@PathParam("id") Long id);
}
