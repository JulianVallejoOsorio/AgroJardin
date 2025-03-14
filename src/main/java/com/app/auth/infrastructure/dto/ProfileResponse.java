package com.app.auth.infrastructure.dto;

public class ProfileResponse {
    private Number id;
    private String name;
    private String email;

    public ProfileResponse(Number id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return this.id.toString();
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
} 