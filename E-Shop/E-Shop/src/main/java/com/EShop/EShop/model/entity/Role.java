package com.EShop.EShop.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "authority", nullable = false)
    private String authority;


    public Role() {
    }

    public String getAuthority() {
        return authority;
    }

    public Role setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

}
