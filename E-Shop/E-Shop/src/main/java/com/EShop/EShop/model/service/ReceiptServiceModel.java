package com.EShop.EShop.model.service;

import com.EShop.EShop.model.entity.Order;
import com.EShop.EShop.model.entity.User;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReceiptServiceModel {

    @NotNull
    private String id;

    @NotNull
    private BigDecimal fee;

    @NotNull
    private LocalDateTime issuedOn;

    @NotNull
    private User recipient;

    @NotNull
    private Order order;

    public ReceiptServiceModel() {
    }

    public String getId() {
        return id;
    }

    public ReceiptServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public ReceiptServiceModel setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public ReceiptServiceModel setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public User getRecipient() {
        return recipient;
    }

    public ReceiptServiceModel setRecipient(User recipient) {
        this.recipient = recipient;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public ReceiptServiceModel setOrder(Order order) {
        this.order = order;
        return this;
    }
}
