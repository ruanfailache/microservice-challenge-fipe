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

@Provider
@Priority(1000)
@ApplicationScoped
public class AuthenticationFilter implements ContainerRequestFilter {
    
    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class);
    
    @Inject
    UserClientPort userClientPort;
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        try {
            UserResponse user = userClientPort.getCurrentUser(authorization);
            requestContext.setProperty("authenticatedUser", user);
        } catch (Exception e) {
            LOG.warnf("Token validation failed: %s", e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

