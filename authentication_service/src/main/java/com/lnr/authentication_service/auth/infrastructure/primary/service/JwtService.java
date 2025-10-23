package com.lnr.authentication_service.auth.infrastructure.primary.service;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Role;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minutes
    private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days

    public JwtService(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /** Generate JWT access token including roles and authorities */
    public String generateAccessToken(UserAccount user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", extractRoles(user));
        claims.put("authorities", extractAuthorities(user));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getPublicId().value().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /** Generate refresh token (simpler, usually no roles/authorities needed) */
    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /** Extract UserId from JWT */
    public UUID extractUserId(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return UUID.fromString(subject);
    }

    // ----------------------
    // Helper methods
    // ----------------------

    private List<String> extractRoles(UserAccount user) {
        if (user.getRoles() == null) return Collections.emptyList();
        return user.getRoles().stream()
                .map(Role::getName)
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    private List<String> extractAuthorities(UserAccount user) {
        if (user.getRoles() == null) return Collections.emptyList();
        return user.getRoles().stream()
                .flatMap(role -> role.getAuthorities().stream())
                .map(auth -> auth.getName().name())
                .distinct()
                .collect(Collectors.toList());
    }
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // --- getters for expiration times ---
    public long getAccessTokenExpiration() {
        return ACCESS_TOKEN_EXPIRATION;
    }

    public long getRefreshTokenExpiration() {
        return REFRESH_TOKEN_EXPIRATION;
    }
}
