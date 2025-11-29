package com.fipe.infrastructure.adapter.out.rest.adapter;

import com.fipe.domain.enums.Role;
import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.port.out.client.UserServiceClientPort;
import com.fipe.infrastructure.adapter.out.rest.request.UserAuthenticationRequest;
import com.fipe.infrastructure.adapter.out.rest.response.UserServiceResponse;
import com.fipe.infrastructure.adapter.out.rest.client.UserServiceRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 * Adapter for user service client operations
 */
@ApplicationScoped
public class UserServiceClientAdapter implements UserServiceClientPort {
    
    private static final Logger LOG = Logger.getLogger(UserServiceClientAdapter.class);
    
    @Inject
    @RestClient
    UserServiceRestClient userServiceRestClient;
    
    /**
     * Validate user credentials via user service
     * 
     * @param username the username
     * @param password the password
     * @return the user's role if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    public Role validateCredentials(String username, String password) {
        try {
            LOG.infof("Validating credentials for user: %s", username);
            
            UserServiceResponse response = userServiceRestClient.validateCredentials(
                new UserAuthenticationRequest(username, password)
            );
            
            if (!response.active()) {
                throw new AuthenticationException("User account is inactive");
            }
            
            LOG.infof("Successfully validated credentials for user: %s", username);
            return Role.fromString(response.role());
            
        } catch (Exception e) {
            LOG.errorf(e, "Failed to validate credentials for user: %s", username);
            throw new AuthenticationException("Invalid credentials");
        }
    }
    
    /**
     * Get user by username
     * 
     * @param username the username
     * @return user response
     */
    public UserServiceResponse getUserByUsername(String username) {
        try {
            LOG.infof("Fetching user by username: %s", username);
            return userServiceRestClient.getUserByUsername(username);
        } catch (Exception e) {
            LOG.errorf(e, "Failed to fetch user: %s", username);
            throw new AuthenticationException("Failed to fetch user information");
        }
    }
}
