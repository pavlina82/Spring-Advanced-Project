package com.EShop.EShop.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public  class User extends BaseEntity  implements UserDetails {


    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "address", nullable = false)
    private String address;


    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;


    public User() {
        this.authorities = new HashSet<>();
    }



    public String getUsername() {
        return username;
    }



    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }


    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return false;
    }


}
