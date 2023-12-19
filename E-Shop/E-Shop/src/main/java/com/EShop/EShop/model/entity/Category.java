package com.EShop.EShop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    @Column(name = "category_name",nullable = false, unique = true)
    private String name;


    private boolean isDeleted;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Category setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
