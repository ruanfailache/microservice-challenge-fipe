package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.LoginResponse;
import com.fipe.infrastructure.adapter.in.rest.openapi.AuthenticationApi;
import com.fipe.infrastructure.security.JwtAuthenticationService;
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
        String token = jwtAuthenticationService.authenticate(
                request.getUsername(), 
                request.getPassword()
        );
        
        // Determine role based on username (demo logic)
        String role = "admin".equals(request.getUsername()) ? "ADMIN" : "USER";
        
        LoginResponse response = new LoginResponse(
                token,
                request.getUsername(),
                role,
                86400 // 24 hours in seconds
        );
        
        return Response.ok(response).build();
    }
}
