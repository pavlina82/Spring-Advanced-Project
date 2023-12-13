package com.EShop.EShop.model.view;

import java.math.BigDecimal;

public class ProductDetailsViewModel {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private BigDecimal discountedPrice;

    public ProductDetailsViewModel() {
    }

    public String getId() {
        return id;
    }

    public ProductDetailsViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDetailsViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductDetailsViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public ProductDetailsViewModel setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }
}
