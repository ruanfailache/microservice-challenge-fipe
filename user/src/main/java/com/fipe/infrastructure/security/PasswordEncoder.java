package com.fipe.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Service for encoding and validating passwords using SHA-256 with salt
 */
@ApplicationScoped
public class PasswordEncoder {
    
    private static final int SALT_LENGTH = 16;
    private static final String ALGORITHM = "SHA-256";
    private final SecureRandom random = new SecureRandom();
    
    /**
     * Encodes a plain text password with a random salt
     * 
     * @param plainPassword the plain text password
     * @return the encoded password with salt (format: salt:hash)
     */
    public String encode(String plainPassword) {
        try {
            byte[] salt = generateSalt();
            byte[] hash = hashPassword(plainPassword, salt);
            
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);
            
            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to encode password", e);
        }
    }
    
    /**
     * Validates a plain text password against an encoded password
     * 
     * @param plainPassword the plain text password
     * @param encodedPassword the encoded password (format: salt:hash)
     * @return true if the password matches, false otherwise
     */
    public boolean matches(String plainPassword, String encodedPassword) {
        try {
            String[] parts = encodedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[1]);
            byte[] actualHash = hashPassword(plainPassword, salt);
            
            return MessageDigest.isEqual(expectedHash, actualHash);
        } catch (Exception e) {
            return false;
        }
    }
    
    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    
    private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        digest.update(salt);
        return digest.digest(password.getBytes());
    }
}
