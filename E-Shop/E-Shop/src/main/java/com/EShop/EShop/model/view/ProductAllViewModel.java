package com.EShop.EShop.model.view;

import java.math.BigDecimal;

public class ProductAllViewModel {

    private String id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private BigDecimal discountedPrice;

    public ProductAllViewModel() {
    }

    public String getId() {
        return id;
    }

    public ProductAllViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductAllViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductAllViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductAllViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public ProductAllViewModel setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }
}
