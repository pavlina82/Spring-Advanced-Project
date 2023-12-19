package com.EShop.EShop.model.view;

import com.EShop.EShop.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReceiptViewModel {

    private String id;
    private BigDecimal fee;
    private LocalDateTime issuedOn;
    private User recipient;

    public ReceiptViewModel() {
    }

    public String getId() {
        return id;
    }

    public ReceiptViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public ReceiptViewModel setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public ReceiptViewModel setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public User getRecipient() {
        return recipient;
    }

    public ReceiptViewModel setRecipient(User recipient) {
        this.recipient = recipient;
        return this;
    }
}
