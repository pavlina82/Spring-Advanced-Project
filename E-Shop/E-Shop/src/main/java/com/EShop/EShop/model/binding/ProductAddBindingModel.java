package com.EShop.EShop.model.binding;

import com.EShop.EShop.model.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class ProductAddBindingModel {

    @NotNull
    @Size(min = 3,max = 20)
    private String name;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private MultipartFile image;

    @NotNull
    @NotEmpty
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
