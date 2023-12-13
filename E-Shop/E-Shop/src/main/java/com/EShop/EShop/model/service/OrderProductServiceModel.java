package com.EShop.EShop.model.service;




import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrderProductServiceModel extends BaseServiceModel{

    @NotNull
    private ProductServiceModel product;

    @NotNull
    private BigDecimal price;

    public OrderProductServiceModel() {
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public OrderProductServiceModel setProduct(ProductServiceModel product) {
        this.product = product;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderProductServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
