package com.example.bachelor.entities.user;

public class ResponseUser {

    private String username;
    private String accessToken;

    public ResponseUser(String username, String authToken) {
        this.username = username;
        this.accessToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
