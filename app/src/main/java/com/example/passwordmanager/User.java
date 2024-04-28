package com.example.passwordmanager;

public class User {

    private int userId;
    private String firstName, lastName, username, password;

    public User(int userId, String firstName, String lastName, String username, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }
}
