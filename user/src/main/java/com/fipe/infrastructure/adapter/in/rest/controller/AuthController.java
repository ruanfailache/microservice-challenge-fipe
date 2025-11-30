package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.infrastructure.adapter.in.rest.dto.response.LoginResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.UserResponseMapper;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.GetUserUseCase;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.quarkus.security.Authenticated;
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
public class AuthController implements AuthApi {
    
    private static final long TOKEN_EXPIRATION_SECONDS = 86400; // 24 hours
    
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
        return Response.ok(userResponseMapper.toResponse(user)).build();
    }
}