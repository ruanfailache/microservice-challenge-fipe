package com.fipe.infrastructure.security;

import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Provider
@Priority(2000)
@ApplicationScoped
public class RoleCheckFilter implements ContainerRequestFilter {
    
    private static final Logger LOG = Logger.getLogger(RoleCheckFilter.class);
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = (Method) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethod");
        if (method == null) {
            return;
        }
        
        RequiresRole requiresRole = method.getAnnotation(RequiresRole.class);
        if (requiresRole == null) {
            requiresRole = method.getDeclaringClass().getAnnotation(RequiresRole.class);
        }
        
        if (requiresRole != null && requiresRole.value().length > 0) {
            UserResponse user = (UserResponse) requestContext.getProperty("authenticatedUser");
            if (user == null) {
                LOG.warn("No authenticated user found for role check");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }
            
            boolean hasRole = Arrays.asList(requiresRole.value()).contains(user.role());
            if (!hasRole) {
                LOG.warnf("User %s does not have required role: %s", user.username(), Arrays.toString(requiresRole.value()));
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                return;
            }
            
            LOG.debugf("Role check passed for user: %s", user.username());
        }
    }
}