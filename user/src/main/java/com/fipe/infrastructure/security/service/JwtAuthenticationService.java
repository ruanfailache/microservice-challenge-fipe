package com.fipe.infrastructure.security.service;

import com.fipe.domain.enums.Role;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.AuthenticateUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.AuthenticationRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.LoginResponse;
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
    
    public LoginResponse authenticate(LoginRequest request) {
        LOG.infof("Authenticating user: %s", request.username());
        User user = authenticateUserUseCase.execute(request);
        String token = generateToken(request.username(), user.getRole());
        return new LoginResponse(token, user.getUsername(), user.getRole().getName());
    }
    
    private String generateToken(String username, Role role) {
        Role validatedRole = role != null ? role : Role.USER;
        return Jwt.issuer(issuer)
                .upn(username)
                .groups(Set.of(validatedRole.getName()))
                .expiresIn(TOKEN_DURATION)
                .sign();
    }
}