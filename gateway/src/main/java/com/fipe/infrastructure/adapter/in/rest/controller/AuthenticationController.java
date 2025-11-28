package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.application.service.AuthenticationService;
import com.fipe.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.LoginResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication operations")
public class AuthenticationController {
    
    @Inject
    AuthenticationService authenticationService;
    
    @POST
    @Path("/login")
    @Operation(summary = "Login", description = "Authenticate and receive JWT token")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = LoginResponse.class))),
            @APIResponse(responseCode = "401", description = "Invalid credentials"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response login(LoginRequest request) {
        try {
            String token = authenticationService.authenticate(
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
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Invalid credentials"))
                    .build();
        }
    }
    
    private static class ErrorResponse {
        private String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
