package com.learn.jwt.model;

/**
 * Simple wrapper so Swagger shows a clean JSON body for /auth/validate
 */
public class TokenBody {

    private String token;

    public TokenBody() {}
    public TokenBody(String token) { this.token = token; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}

