package com.EShop.EShop.model.view;

import java.time.LocalDateTime;

public class MyOrderViewModel {

    private String id;
    private LocalDateTime issuedOn;
    private LocalDateTime statusDate;

    public MyOrderViewModel() {
    }

    public String getId() {
        return id;
    }

    public MyOrderViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public MyOrderViewModel setIssuedOn(LocalDateTime issuedOn) {
        this.issuedOn = issuedOn;
        return this;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public MyOrderViewModel setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
        return this;
    }
}
