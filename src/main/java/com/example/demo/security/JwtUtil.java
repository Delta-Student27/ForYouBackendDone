package com.example.demo.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET ="bXlfc3VwZXJfc2VjcmV0X2tleV9mb3Jfand0X2VuY29kaW5n";

    private SecretKey getSigningKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // UPDATED: Now accepts role and adds it to claims
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // This is the magic line!

<<<<<<< HEAD
=======

    public String generateToken(String email, String role) {
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .claim("role", role)  
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractUsername(token);
        return extractedEmail.equals(email);
    }
}