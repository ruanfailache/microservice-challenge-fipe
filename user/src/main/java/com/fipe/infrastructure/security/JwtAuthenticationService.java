package com.fipe.infrastructure.security;

import com.fipe.domain.enums.Role;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.AuthenticateUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.AuthenticationRequest;
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
    AuthenticateUserUseCase authenticateUserUseCase;
    
    public record AuthenticationResult(String token, String role) {}
    
    public AuthenticationResult authenticate(String username, String password) {
        LOG.infof("Authenticating user: %s", username);
        AuthenticationRequest authRequest = new AuthenticationRequest(username, password);
        User user = authenticateUserUseCase.execute(authRequest);
        String token = generateToken(username, user.getRole());
        return new AuthenticationResult(token, user.getRole().getName());
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