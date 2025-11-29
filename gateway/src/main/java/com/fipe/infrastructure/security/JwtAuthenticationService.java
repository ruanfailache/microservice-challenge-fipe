package com.fipe.infrastructure.security;

import com.fipe.domain.enums.Role;
import com.fipe.domain.port.out.client.UserServiceClientPort;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtAuthenticationService {
    
    private static final Logger LOG = Logger.getLogger(JwtAuthenticationService.class);
    private static final Duration TOKEN_DURATION = Duration.ofHours(24);
    
    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://fipe-issuer")
    String issuer;
    
    @Inject
    UserServiceClientPort userServiceClientAdapter;
    
    public record AuthenticationResult(String token, String role) {}
    
    public AuthenticationResult authenticate(String username, String password) {
        LOG.infof("Authenticating user via user service: %s", username);
        Role role = userServiceClientAdapter.validateCredentials(username, password);
        LOG.infof("User authenticated successfully: %s", username);
        String token = generateToken(username, role);
        return new AuthenticationResult(token, role.getName());
    }
    
    private String generateToken(String username, Role role) {
        String roleName = role != null ? role.getName() : "USER";
        
        return Jwt.issuer(issuer)
                .upn(username)
                .groups(Set.of(roleName))
                .expiresIn(TOKEN_DURATION)
                .sign();
    }
}

