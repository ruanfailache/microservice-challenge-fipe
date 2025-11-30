package com.fipe.infrastructure.adapter.out.rest.adapter;

import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.port.out.client.UserClientPort;
import com.fipe.infrastructure.adapter.out.rest.client.UserClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 * Adapter for user client operations (token validation)
 */
@ApplicationScoped
public class UserClientAdapter implements UserClientPort {
    
    private static final Logger LOG = Logger.getLogger(UserClientAdapter.class);
    
    @Inject
    @RestClient
    UserClient userClient;
    
    @Override
    public boolean validateToken(String authorization) {
        try {
            LOG.debug("Validating token with user service");
            Response response = userClient.validateToken(authorization);
            boolean isValid = response.getStatus() == 200;
            if (isValid) {
                LOG.debug("Token validated successfully");
            } else {
                LOG.warnf("Token validation failed with status: %d", response.getStatus());
            }
            return isValid;
        } catch (Exception e) {
            LOG.warnf("Token validation failed: %s", e.getMessage());
            throw new ExternalServiceException("Failed to validate token with user service", e);
        }
    }
}

