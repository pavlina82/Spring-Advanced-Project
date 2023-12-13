package com.EShop.EShop.service;

import com.EShop.EShop.model.enums.StatusEnums;
import com.EShop.EShop.model.service.OrderServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(OrderServiceModel orderServiceModel);

    void changeOrderStatus(Long id);


    List<OrderServiceModel> findAllOrders();

    OrderServiceModel findOrderById(Long id);

    List<OrderServiceModel> findOrdersByCustomer(String customerName);

    List<OrderServiceModel> findOrdersByCustomerAndStatus(String customerName, StatusEnums statusEnums);

    List<OrderServiceModel> findOrdersByStatus(StatusEnums statusEnums);
}
