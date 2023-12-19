package com.EShop.EShop.model.view;

public class UserProfileViewModel {

    private String username;
    private String email;

    public UserProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserProfileViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileViewModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
