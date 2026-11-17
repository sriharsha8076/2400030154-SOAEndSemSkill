package com.learn.jwt.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    private static final String SECRET = "MySuperSecretKeyForJwtDemoLearning12345!";

    private static final long EXPIRATION_MS = 60 * 60 * 1000L;

    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);

    public String generateToken(String username, String role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);

        return Jwts.builder()
                .claims(extraClaims)          // custom claims first
                .subject(username)            // 'sub' claim
                .issuedAt(new Date())         // 'iat' claim
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // 'exp'
                .signWith(getSigningKey())    // sign with HS256
                .compact();                  // serialize to string
    }

 boolean validateToken(String token) {
        try {
            parseToken(token);   // throws if invalid
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Malformed token: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Token error: " + e.getMessage());
        }
        return false;
    }


    public Map<String, Object> extractAllClaims(String token) {
        Claims claims = parseToken(token);
        return new HashMap<>(claims); // Claims extends Map<String,Object>
    }


    public String extractClaim(String token, String claimKey) {
        Claims claims = parseToken(token);
        Object value = claims.get(claimKey);
        return value != null ? value.toString() : null;
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())   // set the key for verification
                .build()
                .parseSignedClaims(token)      // parse & verify (throws on error)
                .getPayload();                 // return the claims body
    }
}

