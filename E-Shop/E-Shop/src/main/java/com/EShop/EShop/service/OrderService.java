package com.EShop.EShop.service;

import com.EShop.EShop.model.service.OrderServiceModel;

public interface OrderService {

    void createOrder(OrderServiceModel orderServiceModel);

    void changeOrderStatus(Long id);
}
