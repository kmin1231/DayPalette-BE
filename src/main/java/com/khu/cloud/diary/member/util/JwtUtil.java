package com.khu.cloud.diary.member.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger; // ^^^
import org.slf4j.LoggerFactory; // ^^^
import java.util.Arrays; // ^^^

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class); // ^^^

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        logger.info("[JwtUtil init] Attempting to initialize with secret: '{}'", secret); // ^^^
        if (secret == null || secret.isEmpty()) {
            logger.error("[JwtUtil init] JWT secret is null or empty!"); // ^^^
            throw new IllegalArgumentException("JWT secret must not be null or empty");
        }
        try { // ^^^
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8); // ^^^
            logger.info("[JwtUtil init] Secret string converted to bytes (UTF-8). Length: {}. First 5 bytes (if available): {}", keyBytes.length, bytesToHex(keyBytes, 5)); // ^^^
            this.key = Keys.hmacShaKeyFor(keyBytes);
            logger.info("[JwtUtil init] Key initialized successfully. Key algorithm: {}. Key (first 5 encoded bytes hex): {}", // ^^^
                    (this.key != null ? this.key.getAlgorithm() : "null"), // ^^^
                    (this.key != null ? bytesToHex(this.key.getEncoded(), 5) : "null")); // ^^^
        } catch (Exception e) { // ^^^
            logger.error("[JwtUtil init] Failed to create key from secret. Secret: '{}', Error: {}", secret, e.getMessage(), e); // ^^^
            throw e; // ^^^
        } // ^^^
    }

    public String generateToken(String email) {
        if (this.key == null) { // ^^^
            logger.error("[JwtUtil generateToken] Key is null! Cannot generate token. Init might have failed."); // ^^^
            throw new IllegalStateException("JwtUtil key has not been initialized."); // ^^^
        } // ^^^
        logger.info("[JwtUtil generateToken] Using Key (instance: {}, first 5 encoded bytes hex: {}) to sign token for email: {}", System.identityHashCode(this.key), bytesToHex(this.key.getEncoded(), 5), email); // ^^^
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expirationDate = new Date(now + expiration);

        return Jwts.builder()
                .claim("sub", email)
                .claim("iat", issuedAt.getTime())
                .claim("exp", expirationDate.getTime())
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token) {
        if (this.key == null) { // ^^^
            logger.error("[JwtUtil extractClaims] Key is null! Cannot extract claims. Init might have failed."); // ^^^
            throw new IllegalStateException("JwtUtil key has not been initialized."); // ^^^
        } // ^^^
        logger.info("[JwtUtil extractClaims] Using Key (instance: {}, first 5 encoded bytes hex: {}) to parse token", System.identityHashCode(this.key), bytesToHex(this.key.getEncoded(), 5)); // ^^^
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, String email) {
        try {
            String extractedEmail = extractEmail(token);
            boolean isEmailMatch = email.equals(extractedEmail); // ^^^
            boolean isNotExpired = !isTokenExpired(token); // ^^^
            logger.info("[JwtUtil validateToken] Email from UserDetails: '{}', Email from Token: '{}', Email Match: {}, Not Expired: {}", email, extractedEmail, isEmailMatch, isNotExpired); // ^^^
            return (isEmailMatch && isNotExpired);
        } catch (Exception e) {
            logger.warn("[JwtUtil validateToken] Token validation failed for user {}. Token: [{}...], Error: {}", email, token.substring(0, Math.min(token.length(), 10)), e.getMessage());
            return false;
        }
    }

    private static String bytesToHex(byte[] bytes, int limit) { // ^^^
        if (bytes == null) return "null_byte_array"; // ^^^
        StringBuilder hexString = new StringBuilder(2 * Math.min(bytes.length, limit)); // ^^^
        for (int i = 0; i < bytes.length && i < limit; i++) { // ^^^
            String hex = Integer.toHexString(0xff & bytes[i]); // ^^^
            if (hex.length() == 1) { // ^^^
                hexString.append('0'); // ^^^
            } // ^^^
            hexString.append(hex); // ^^^
        } // ^^^
        if (bytes.length > limit) { // ^^^
            hexString.append("..."); // ^^^
        } // ^^^
        return hexString.toString(); // ^^^
    } // ^^^
}