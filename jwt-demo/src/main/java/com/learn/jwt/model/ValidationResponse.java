package com.learn.jwt.model;

import java.util.Map;

/**
 * Response returned by the /auth/validate endpoint.
 * Plain Java — no Lombok needed.
 */
public class ValidationResponse {

    private boolean valid;
    private String message;
    private Map<String, Object> claims;

    public ValidationResponse() {}

    public ValidationResponse(boolean valid, String message, Map<String, Object> claims) {
        this.valid = valid;
        this.message = message;
        this.claims = claims;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Map<String, Object> getClaims() { return claims; }
    public void setClaims(Map<String, Object> claims) { this.claims = claims; }
}
