package com.EShop.EShop.model.view;

import java.util.Set;

public class UsersViewModel {
    private String id;
    private String username;
    private String email;
    private String address;
    private Set<RoleViewModel> authorities;

    public String getId() {
        return id;
    }

    public UsersViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UsersViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UsersViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UsersViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<RoleViewModel> getAuthorities() {
        return authorities;
    }

    public UsersViewModel setAuthorities(Set<RoleViewModel> authorities) {
        this.authorities = authorities;
        return this;
    }
}
