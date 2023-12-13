package com.EShop.EShop.model.view;

import java.math.BigDecimal;

public class OrderProductViewModel {

    private ProductDetailsViewModel product;
    private BigDecimal price;

    public OrderProductViewModel() {
    }

    public ProductDetailsViewModel getProduct() {
        return product;
    }

    public OrderProductViewModel setProduct(ProductDetailsViewModel product) {
        this.product = product;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderProductViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
