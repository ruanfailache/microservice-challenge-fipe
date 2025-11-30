package com.fipe.infrastructure.security.filter;

import com.fipe.domain.port.out.client.UserClientPort;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

@Provider
@Priority(1000)
@ApplicationScoped
public class UserServiceAuthFilter implements ContainerRequestFilter {
    
    private static final Logger LOG = Logger.getLogger(UserServiceAuthFilter.class);
    
    @Inject
    UserClientPort userClientPort;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        
        try {
            UserResponse user = userClientPort.validateToken(authHeader);
            requestContext.setProperty("authenticatedUser", user);
            LOG.debugf("Token validated for user: %s with role: %s", user.username(), user.role());
        } catch (Exception e) {
            LOG.warnf("Token validation failed: %s", e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

