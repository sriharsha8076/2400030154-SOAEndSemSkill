package com.learn.jwt.model;

/**
 * Request body for /auth/generate — username + role for demo.
 * No Lombok — plain Java so there are zero annotation processing issues.
 */
public class TokenRequest {

    private String username;
    private String role;

    public TokenRequest() {}

    public TokenRequest(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
