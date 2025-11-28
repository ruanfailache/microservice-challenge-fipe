package com.fipe.application.service;

import com.fipe.domain.exception.AuthenticationException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class AuthenticationService {
    
    private static final Logger LOG = Logger.getLogger(AuthenticationService.class);
    private static final Duration TOKEN_DURATION = Duration.ofHours(24);
    
    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://fipe-issuer")
    String issuer;
    
    public String authenticate(String username, String password) {
        LOG.infof("Authenticating user: %s", username);
        
        // In production, validate against database
        // For demo purposes, we accept admin/admin and user/user
        String role = validateCredentials(username, password);
        
        if (role == null) {
            throw new AuthenticationException("Invalid credentials");
        }
        
        return generateToken(username, role);
    }
    
    private String validateCredentials(String username, String password) {
        // Demo credentials - replace with real authentication
        if ("admin".equals(username) && "admin".equals(password)) {
            return "ADMIN";
        } else if ("user".equals(username) && "user".equals(password)) {
            return "USER";
        }
        return null;
    }
    
    private String generateToken(String username, String role) {
        return Jwt.issuer(issuer)
                .upn(username)
                .groups(new HashSet<>(Arrays.asList(role)))
                .expiresIn(TOKEN_DURATION)
                .sign();
    }
}
