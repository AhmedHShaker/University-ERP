package com.university.erp.model;

public class JwtResponse {
    private final String token;
    private final String role;
    private final long userId;

    public JwtResponse(String token, long userId, String role) {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    public String getToken() {
        return this.token;
    }

    public String getRole() {
        return this.role;
    }

    public long getUserId() {
        return this.userId;
    }
}
