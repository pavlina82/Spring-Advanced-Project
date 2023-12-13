package com.EShop.EShop.model.entity;


import com.EShop.EShop.model.enums.StatusEnums;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "customer_id"
    )
    private User customer;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "issued_on", nullable = false)
    private LocalDateTime issuedOn;

    @Column(name = "status_date")
    private LocalDateTime statusDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_enums", nullable = false)
    private StatusEnums status;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @ManyToMany(targetEntity = OrderProduct.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(
                    name = "order_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "product_id"
            )
    )
    private List<OrderProduct> products;

    public Order() {
    }

    public User getCustomer() {
        return customer;
    }

    public Order setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public Order setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public Order setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public StatusEnums getStatus() {
        return status;
    }

    public Order setStatus(StatusEnums status) {
        this.status = status;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Order setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public Order setProducts(List<OrderProduct> products) {
        this.products = products;
        return this;
    }
}
