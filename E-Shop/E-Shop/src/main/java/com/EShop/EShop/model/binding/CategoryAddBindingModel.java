package com.EShop.EShop.model.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CategoryAddBindingModel {

    @NotNull
    @NotEmpty
    @Length(max = 20)
    private String name;

    public String getName() {
        return name;
    }

    public CategoryAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }
}
