package com.EShop.EShop.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_product")
public class OrderProduct extends BaseEntity{

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(
            name = "product_id"
    )
    private Product product;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT '0.00'")
    private BigDecimal price;

    public OrderProduct() {
    }

    public Product getProduct() {
        return product;
    }

    public OrderProduct setProduct(Product product) {
        this.product = product;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderProduct setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
