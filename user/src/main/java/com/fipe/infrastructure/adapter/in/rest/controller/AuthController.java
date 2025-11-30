package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.GetUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.LoginResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.UserResponseMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.AuthApi;
import com.fipe.infrastructure.security.JwtAuthenticationService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController implements AuthApi {
    
    private static final long TOKEN_EXPIRATION_SECONDS = 24 * 60 * 60;
    
    @Inject
    JwtAuthenticationService jwtAuthenticationService;
    
    @Inject
    GetUserUseCase getUserUseCase;
    
    @Inject
    JsonWebToken jwt;
    
    @Inject
    UserResponseMapper userResponseMapper;
    
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        var authResult = jwtAuthenticationService.authenticate(
                request.getUsername(), 
                request.getPassword()
        );
        
        LoginResponse response = new LoginResponse(
                authResult.token(),
                request.getUsername(),
                authResult.role(),
                TOKEN_EXPIRATION_SECONDS
        );
        
        return Response.ok(response).build();
    }
    
    @POST
    @Path("/validate-token")
    @Authenticated
    public Response validateToken() {
        String username = jwt.getName();
        User user = getUserUseCase.getByUsername(username);
        UserResponse response = userResponseMapper.toResponse(user);
        return Response.ok(response).build();
    }
}