package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.LoginResponse;
import com.fipe.infrastructure.adapter.in.rest.openapi.AuthenticationApi;
import com.fipe.infrastructure.security.JwtAuthenticationService;
import jakarta.annotation.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController implements AuthenticationApi {
    
    @Inject
    JwtAuthenticationService jwtAuthenticationService;
    
    @POST
    @Path("/login")
    @Override
    public Response login(LoginRequest request) {
        var authResult = jwtAuthenticationService.authenticate(
                request.getUsername(), 
                request.getPassword()
        );
        
        LoginResponse response = new LoginResponse(
                authResult.token(),
                request.getUsername(),
                authResult.role(),
                86400 // 24 hours in seconds
        );
        
        return Response.ok(response).build();
    }
    
    @POST
    @Path("/validate-token")
    @Authenticated
    public Response validateToken() {
        // If the request reaches here, the token is valid
        return Response.ok().build();
    }
}