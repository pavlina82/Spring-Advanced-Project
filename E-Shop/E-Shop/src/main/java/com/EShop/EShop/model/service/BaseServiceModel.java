package com.EShop.EShop.model.service;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseServiceModel {

    private Long id;

    public BaseServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public BaseServiceModel setId(Long id) {
        this.id = id;
        return this;
    }
}
