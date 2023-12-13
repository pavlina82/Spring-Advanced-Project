package com.EShop.EShop.model.view;

public class CategoryViewModel {

    private String id;
    private String name;

    public CategoryViewModel() {
    }

    public String getId() {
        return id;
    }

    public CategoryViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
