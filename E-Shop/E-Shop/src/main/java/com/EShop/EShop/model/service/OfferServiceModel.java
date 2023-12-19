package com.EShop.EShop.model.service;



import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OfferServiceModel extends BaseServiceModel{

    @NotNull
    private ProductServiceModel product;

    @NotNull
    private BigDecimal price;

    public OfferServiceModel() {
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public OfferServiceModel setProduct(ProductServiceModel product) {
        this.product = product;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
