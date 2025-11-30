package com.fipe.infrastructure.adapter.in.rest.interceptor;

import com.fipe.infrastructure.adapter.in.rest.service.UserAuthService;
import com.fipe.infrastructure.adapter.out.rest.client.UserAuthClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JwtValidationFilter implements ContainerRequestFilter {
    
    @Inject
    UserAuthService userAuthService;
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        
        // Skip authentication for certain paths
        if (path.startsWith("/swagger") || path.startsWith("/health") || path.startsWith("/q/") || path.equals("/api/v1/auth/login")) {
            return;
        }
        
        String authorization = requestContext.getHeaderString("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        
        try {
            UserAuthClient userAuthClient = userAuthService.getUserAuthClient();
            userAuthClient.validateToken(authorization);
            // If we get here, token is valid
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}