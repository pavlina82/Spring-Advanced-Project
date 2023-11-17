package com.EShop.EShop.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
public class Receipt extends BaseEntity{

    @Column(name = "fee", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT '0.00'")
    private BigDecimal fee;

    @Column(name = "issued_on", nullable = false)
    private LocalDateTime issuedOn;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "recipient_id",
            referencedColumnName = "id"
    )
    private User recipient;

    @OneToOne(targetEntity = Order.class)
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "id"
    )
    private Order order;

    public Receipt() {
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Receipt setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public Receipt setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public User getRecipient() {
        return recipient;
    }

    public Receipt setRecipient(User recipient) {
        this.recipient = recipient;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public Receipt setOrder(Order order) {
        this.order = order;
        return this;
    }
}
