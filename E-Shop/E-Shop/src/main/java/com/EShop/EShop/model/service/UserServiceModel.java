package com.EShop.EShop.model.service;

import com.EShop.EShop.model.entity.Role;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel{


    private String username;
    private String password;
    private String address;
    private String email;
    private Set<Role> authorities;

    public UserServiceModel() {
        this.authorities = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public UserServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserServiceModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public UserServiceModel setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
        return this;
    }
}
