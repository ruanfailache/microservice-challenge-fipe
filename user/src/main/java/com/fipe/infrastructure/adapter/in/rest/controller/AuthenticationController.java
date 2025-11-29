package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.AuthenticateUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.AuthenticationRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * REST controller for authentication operations
 */
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "User authentication operations")
public class AuthenticationController {
    
    @Inject
    AuthenticateUserUseCase authenticateUserUseCase;
    
    @POST
    @Path("/validate")
    @Operation(summary = "Validate user credentials", description = "Validates username and password, returns user if valid")
    public Response validateCredentials(AuthenticationRequest request) {
        User user = authenticateUserUseCase.execute(
            new AuthenticateUserUseCase.AuthenticationRequest(
                request.username(),
                request.password()
            )
        );
        
        return Response.ok(UserResponse.fromDomain(user)).build();
    }
    
    @POST
    @Path("/authenticate")
    @Operation(summary = "Authenticate user", description = "Authenticates user and returns user data")
    public Response authenticate(AuthenticationRequest request) {
        User user = authenticateUserUseCase.execute(
            new AuthenticateUserUseCase.AuthenticationRequest(
                request.username(),
                request.password()
            )
        );
        
        return Response.ok(UserResponse.fromDomain(user)).build();
    }
}
