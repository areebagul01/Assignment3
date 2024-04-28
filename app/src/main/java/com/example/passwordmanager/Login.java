package com.example.passwordmanager;

public class Login {

    private int id, userId;
    private String websiteUrl, username, password;

    public Login(int id, int userId, String websiteUrl, String username, String password) {
        this.id = id;
        this.userId = userId;
        this.websiteUrl = websiteUrl;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
