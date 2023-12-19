package com.EShop.EShop.model.view;

import java.math.BigDecimal;

public class OfferViewModel {

    private ProductDetailsViewModel product;
    private BigDecimal price;

    public OfferViewModel() {
    }

    public ProductDetailsViewModel getProduct() {
        return product;
    }

    public OfferViewModel setProduct(ProductDetailsViewModel product) {
        this.product = product;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
