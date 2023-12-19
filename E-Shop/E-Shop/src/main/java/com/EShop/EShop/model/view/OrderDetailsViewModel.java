package com.EShop.EShop.model.view;

import com.EShop.EShop.model.entity.User;
import com.EShop.EShop.model.enums.StatusEnums;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailsViewModel {

    private String id;
    private LocalDateTime issuedOn;
    private LocalDateTime statusDate;
    private String shippingAddress;
    private List<ShoppingCartItem> items;
    private StatusEnums status;
    private BigDecimal totalPrice;
    private User customer;

    public OrderDetailsViewModel() {
    }

    public String getId() {
        return id;
    }

    public OrderDetailsViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public OrderDetailsViewModel setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public OrderDetailsViewModel setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public OrderDetailsViewModel setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public OrderDetailsViewModel setItems(List<ShoppingCartItem> items) {
        this.items = items;
        return this;
    }

    public StatusEnums getStatus() {
        return status;
    }

    public OrderDetailsViewModel setStatus(StatusEnums status) {
        this.status = status;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderDetailsViewModel setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public User getCustomer() {
        return customer;
    }

    public OrderDetailsViewModel setCustomer(User customer) {
        this.customer = customer;
        return this;
    }
}
