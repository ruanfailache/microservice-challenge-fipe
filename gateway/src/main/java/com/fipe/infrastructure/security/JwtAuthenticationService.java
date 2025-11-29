package com.fipe.infrastructure.security;

import com.fipe.domain.enums.Role;
import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.model.User;
import com.fipe.domain.port.out.UserRepositoryPort;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@ApplicationScoped
public class JwtAuthenticationService {
    
    private static final Logger LOG = Logger.getLogger(JwtAuthenticationService.class);
    private static final Duration TOKEN_DURATION = Duration.ofHours(24);
    
    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://fipe-issuer")
    String issuer;
    
    @Inject
    UserRepositoryPort userRepository;
    
    @Inject
    PasswordEncoder passwordEncoder;
    
    @Transactional
    public String authenticate(String username, String password) {
        LOG.infof("Authenticating user: %s", username);
        
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new AuthenticationException("Invalid credentials"));
        
        if (!user.isActive()) {
            throw new AuthenticationException("User account is inactive");
        }
        
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new AuthenticationException("Invalid credentials");
        }
        
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.update(user);
        
        return generateToken(username, user.getRole());
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
