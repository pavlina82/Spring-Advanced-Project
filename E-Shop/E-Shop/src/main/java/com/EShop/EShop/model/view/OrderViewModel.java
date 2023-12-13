package com.EShop.EShop.model.view;

import com.EShop.EShop.model.enums.StatusEnums;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderViewModel {

    private String id;
    private List<OrderProductViewModel> products;
    private UserProfileViewModel customer;
    private BigDecimal totalPrice;
    private LocalDateTime issuedOn;
    private LocalDateTime statusDate;
    private StatusEnums status;

    public OrderViewModel() {
    }

    public String getId() {
        return id;
    }

    public OrderViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public List<OrderProductViewModel> getProducts() {
        return products;
    }

    public OrderViewModel setProducts(List<OrderProductViewModel> products) {
        this.products = products;
        return this;
    }

    public UserProfileViewModel getCustomer() {
        return customer;
    }

    public OrderViewModel setCustomer(UserProfileViewModel customer) {
        this.customer = customer;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderViewModel setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public OrderViewModel setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public OrderViewModel setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public StatusEnums getStatus() {
        return status;
    }

    public OrderViewModel setStatus(StatusEnums status) {
        this.status = status;
        return this;
    }
}
