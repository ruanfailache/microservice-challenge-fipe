package com.fipe.infrastructure.adapter.out.rest.adapter;

import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.port.out.client.UserClientPort;
import com.fipe.infrastructure.adapter.out.rest.client.UserClient;
import com.fipe.infrastructure.adapter.out.rest.dto.response.user.UserOutResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserClientAdapter implements UserClientPort {
    
    private static final Logger LOG = Logger.getLogger(UserClientAdapter.class);
    
    @Inject
    @RestClient
    UserClient userClient;
    
    @Override
    public UserOutResponse getCurrentUser(String authorization) {
        try {
            UserOutResponse user = userClient.getCurrentUser(authorization);
            LOG.debugf("Token validated successfully for user: %s", user.username());
            return user;
        } catch (Exception e) {
            LOG.warnf("Token validation failed: %s", e.getMessage());
            throw new AuthenticationException("Invalid or expired token", e);
        }
    }
}
