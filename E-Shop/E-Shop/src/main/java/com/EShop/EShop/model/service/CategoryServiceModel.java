package com.EShop.EShop.model.service;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;



public class CategoryServiceModel extends BaseServiceModel {

    @NotNull
    @NotEmpty
    @Length(max = 20)
    private String name;


    private boolean isDeleted;

    public CategoryServiceModel() {
    }

    public String getName() {
        return name;
    }

    public CategoryServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public CategoryServiceModel setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }


}
