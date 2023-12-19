package com.EShop.EShop.model.service;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;


import java.math.BigDecimal;
import java.util.List;

public class ProductServiceModel extends BaseServiceModel{

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
    private String imageUrl;

    @NotNull
    @NotEmpty
    private List<CategoryServiceModel> categories;


    private BigDecimal discountedPrice;
    private boolean isDeleted;

    public ProductServiceModel() {

    }

    public String getName() {
        return name;
    }

    public ProductServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public List<CategoryServiceModel> getCategories() {
        return categories;
    }

    public ProductServiceModel setCategories(List<CategoryServiceModel> categories) {
        this.categories = categories;
        return this;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public ProductServiceModel setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public ProductServiceModel setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
