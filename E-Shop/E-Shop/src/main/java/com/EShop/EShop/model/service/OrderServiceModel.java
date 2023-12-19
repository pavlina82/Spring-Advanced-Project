package com.EShop.EShop.model.service;

import com.EShop.EShop.model.enums.StatusEnums;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel extends BaseServiceModel{

    @NotNull
    private List<OrderProductServiceModel> products;

    @NotNull
    private UserServiceModel customer;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private LocalDateTime issuedOn;

    @NotNull
    private LocalDateTime statusDate;

    @NotNull
    private String shippingAddress;

    @NotNull
    private StatusEnums status;

    public OrderServiceModel() {
    }

    public List<OrderProductServiceModel> getProducts() {
        return products;
    }

    public OrderServiceModel setProducts(List<OrderProductServiceModel> products) {
        this.products = products;
        return this;
    }

    public UserServiceModel getCustomer() {
        return customer;
    }

    public OrderServiceModel setCustomer(UserServiceModel customer) {
        this.customer = customer;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderServiceModel setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public OrderServiceModel setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public OrderServiceModel setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public OrderServiceModel setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public StatusEnums getStatus() {
        return status;
    }

    public OrderServiceModel setStatus(StatusEnums status) {
        this.status = status;
        return this;
    }
}
