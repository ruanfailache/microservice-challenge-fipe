package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.GetUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.LoginResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.UserResponseMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.AuthApi;
import com.fipe.infrastructure.security.JwtAuthenticationService;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController implements AuthApi {

    @Inject
    JwtAuthenticationService jwtAuthenticationService;
    
    @Inject
    GetUserUseCase getUserUseCase;
    
    @Inject
    UserResponseMapper userResponseMapper;
    
    @Inject
    JWTParser jwtParser;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        var authResult = jwtAuthenticationService.authenticate(
                request.username(),
                request.password()
        );
        LoginResponse response = new LoginResponse(
                authResult.token(),
                request.username(),
                authResult.role()
        );
        return Response.ok(response).build();
    }
    
    @POST
    @Path("/validate-token")
    public Response validateToken(@HeaderParam("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String token = authorization.substring(7);
        try {
            JsonWebToken parsedJwt = jwtParser.parse(token);
            String username = parsedJwt.getName();
            User user = getUserUseCase.getByUsername(username);
            UserResponse response = userResponseMapper.toResponse(user);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}