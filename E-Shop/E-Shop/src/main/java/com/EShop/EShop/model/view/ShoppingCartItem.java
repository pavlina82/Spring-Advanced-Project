package com.EShop.EShop.model.view;



import com.EShop.EShop.model.service.OrderProductServiceModel;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

    private OrderProductServiceModel product;
    private int quantity;

    public ShoppingCartItem() {
    }

    public OrderProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(OrderProductServiceModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
