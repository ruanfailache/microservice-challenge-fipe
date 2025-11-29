package com.fipe.infrastructure.adapter.in.rest.interceptor;

import com.fipe.infrastructure.adapter.out.rest.client.UserAuthClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Provider
public class JwtValidationFilter implements ContainerRequestFilter {
    
    @Inject
    @RestClient
    UserAuthClient userAuthClient;
    
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
            Response response = userAuthClient.validateToken(authorization);
            if (response.getStatus() != 200) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}