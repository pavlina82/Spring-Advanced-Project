package com.EShop.EShop.repository;

import com.EShop.EShop.model.entity.Order;
import com.EShop.EShop.model.enums.StatusEnums;
import com.EShop.EShop.model.service.OrderServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<OrderServiceModel> findAllOrdersByCustomer_UsernameOrderByIssuedOn(String customerName);

    List<OrderServiceModel> findAllOrdersByCustomerUsernameAndStatus_OrderByIssuedOn(String customerName, StatusEnums statusEnums);

    List<OrderServiceModel> findAllOrdersByStatus_OrderByIssuedOn(StatusEnums statusEnums);
}
