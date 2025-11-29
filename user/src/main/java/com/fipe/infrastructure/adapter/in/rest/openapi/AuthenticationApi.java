package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.fipe.infrastructure.adapter.in.rest.dto.response.LoginResponse;

@Tag(name = "Authentication", description = "Authentication operations")
public interface AuthenticationApi {
    
    @Operation(summary = "Login", description = "Authenticate and receive JWT token")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = LoginResponse.class))),
            @APIResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    Response login(LoginRequest request);
}