package com.fipe.infrastructure.adapter.out.rest.adapter;

import com.fipe.domain.enums.Role;
import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.port.out.client.UserClientPort;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.out.rest.response.UserServiceResponse;
import com.fipe.infrastructure.adapter.out.rest.client.UserClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 * Adapter for user client operations
 */
@ApplicationScoped
public class UserClientAdapter implements UserClientPort {
    
    private static final Logger LOG = Logger.getLogger(UserClientAdapter.class);
    
    @Inject
    @RestClient
    UserClient userClient;
    
    @Override
    public UserResponse validateToken(String authorization) {
        try {
            UserResponse user = userClient.validateToken(authorization);
            LOG.debugf("Token validated successfully for user: %s", user.username());
            return user;
        } catch (Exception e) {
            LOG.warnf("Token validation failed: %s", e.getMessage());
            throw new AuthenticationException("Invalid or expired token", e);
        }
    }
}
