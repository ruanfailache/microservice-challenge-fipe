package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.GetUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.UserResponseMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.AuthApi;
import com.fipe.infrastructure.security.service.JwtAuthenticationService;
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

    @Inject
    JwtAuthenticationService jwtAuthenticationService;
    
    @Inject
    GetUserUseCase getUserUseCase;
    
    @Inject
    UserResponseMapper userResponseMapper;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        var response = jwtAuthenticationService.authenticate(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("/current-user")
    @Authenticated
    public Response currentUser() {
        String username = jwt.getName();
        User user = getUserUseCase.getByUsername(username);
        UserResponse response = userResponseMapper.toResponse(user);
        return Response.ok(response).build();
    }
}