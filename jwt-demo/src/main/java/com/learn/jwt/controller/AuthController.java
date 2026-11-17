package com.learn.jwt.controller;

import com.learn.jwt.model.TokenBody;
import com.learn.jwt.model.TokenRequest;
import com.learn.jwt.model.ValidationResponse;
import com.learn.jwt.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ─────────────────────────────────────────────────────────
 *  AuthController — REST endpoints for the JWT demo
 * ─────────────────────────────────────────────────────────
 *
 *  POST  /auth/generate    → creates a JWT for a username + role
 *  POST  /auth/validate    → Task 2: validates token & returns claims
 *  GET   /auth/claims      → extracts all claims from a token
 *  GET   /protected        → example of a "protected" resource
 */
@RestController
@Tag(name = "JWT Auth", description = "Generate, validate and inspect JWT tokens")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // ─────────────────────────────────────────────────────────────────────
    // ENDPOINT 1 — Generate a token (helper so you get a real token to test)
    // ─────────────────────────────────────────────────────────────────────
    @Operation(
        summary = "Step 1 – Generate JWT",
        description = "Creates a signed JWT. Copy the 'token' value from the response and use it in /auth/validate."
    )
    @PostMapping("/auth/generate")
    public ResponseEntity<Map<String, String>> generate(@RequestBody TokenRequest request) {
        String token = jwtService.generateToken(request.getUsername(), request.getRole());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "tip",   "Copy the token above and paste it into /auth/validate"
        ));
    }

    // ─────────────────────────────────────────────────────────────────────
    // ENDPOINT 2 — Validate token  ← Task 2
    // ─────────────────────────────────────────────────────────────────────
    @Operation(
        summary = "Step 2 – Validate JWT",
        description = "Verifies the token signature and expiry, then extracts and returns all claims."
    )
    @PostMapping("/auth/validate")
    public ResponseEntity<ValidationResponse> validate(@RequestBody TokenBody body) {

        String token = body.getToken();
        boolean isValid = jwtService.validateToken(token);

        if (!isValid) {
            return ResponseEntity.ok(
                new ValidationResponse(false, "Token is INVALID or EXPIRED", null)
            );
        }

        Map<String, Object> claims = jwtService.extractAllClaims(token);
        return ResponseEntity.ok(
            new ValidationResponse(true, "Token is VALID", claims)
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    // ENDPOINT 3 — Extract all claims from token (query param)
    // ─────────────────────────────────────────────────────────────────────
    @Operation(
        summary = "Step 3 – Extract Claims",
        description = "Returns every claim (sub, role, iat, exp) from a valid JWT passed as a query param."
    )
    @GetMapping("/auth/claims")
    public ResponseEntity<?> getClaims(
            @Parameter(description = "Paste the JWT token here")
            @RequestParam String token) {

        if (!jwtService.validateToken(token)) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        return ResponseEntity.ok(jwtService.extractAllClaims(token));
    }

    // ─────────────────────────────────────────────────────────────────────
    // ENDPOINT 4 — Simulated protected resource
    // ─────────────────────────────────────────────────────────────────────
    @Operation(
        summary = "Step 4 – Protected Resource",
        description = "Simulates a backend resource. Returns data only if the JWT is valid."
    )
    @GetMapping("/protected")
    public ResponseEntity<Map<String, Object>> protectedResource(
            @Parameter(description = "JWT token (without Bearer prefix)")
            @RequestParam String token) {

        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of(
                "error",  "Access Denied",
                "reason", "Token is invalid or expired"
            ));
        }

        String username = jwtService.extractClaim(token, "sub");
        String role     = jwtService.extractClaim(token, "role");

        return ResponseEntity.ok(Map.of(
            "message",  "Welcome to the protected resource!",
            "username", username,
            "role",     role,
            "data",     "Secret data - visible only with a valid JWT"
        ));
    }
}
