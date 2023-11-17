package com.EShop.EShop.model.binding;

import com.EShop.EShop.model.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class ProductAddBindingModel {

    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private List<Category> categories;

    public String getName() {
        return name;
    }

    public ProductAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public ProductAddBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public ProductAddBindingModel setCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }
}
