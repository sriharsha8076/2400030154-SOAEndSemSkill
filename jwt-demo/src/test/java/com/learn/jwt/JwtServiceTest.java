package com.learn.jwt;

import com.learn.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtService.generateToken("alice", "ADMIN");
        assertNotNull(token);
        System.out.println("Generated token: " + token);

        boolean valid = jwtService.validateToken(token);
        assertTrue(valid, "Token should be valid right after generation");
    }

    @Test
    void testExtractClaims() {
        String token = jwtService.generateToken("bob", "USER");

        String subject = jwtService.extractClaim(token, "sub");
        String role    = jwtService.extractClaim(token, "role");

        assertEquals("bob", subject);
        assertEquals("USER", role);
    }

    @Test
    void testExtractAllClaims() {
        String token = jwtService.generateToken("carol", "MANAGER");

        Map<String, Object> claims = jwtService.extractAllClaims(token);
        System.out.println("All claims: " + claims);

        assertTrue(claims.containsKey("sub"));
        assertTrue(claims.containsKey("role"));
        assertTrue(claims.containsKey("iat"));
        assertTrue(claims.containsKey("exp"));
        assertEquals("carol", claims.get("sub"));
    }

    @Test
    void testInvalidTokenReturnsFalse() {
        boolean valid = jwtService.validateToken("this.is.not.a.valid.token");
        assertFalse(valid, "Garbage token should fail validation");
    }

    @Test
    void testTamperedTokenReturnsFalse() {
        String token = jwtService.generateToken("hacker", "ADMIN");
        String tampered = token.substring(0, token.length() - 5) + "XXXXX";
        boolean valid = jwtService.validateToken(tampered);
        assertFalse(valid, "Tampered token should fail signature check");
    }
}
