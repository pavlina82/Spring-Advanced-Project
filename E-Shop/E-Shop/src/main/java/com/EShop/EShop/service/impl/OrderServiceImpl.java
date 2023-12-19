package com.EShop.EShop.service.impl;

import com.EShop.EShop.exception.OrderNotFoundException;
import com.EShop.EShop.model.entity.Order;
import com.EShop.EShop.model.enums.StatusEnums;
import com.EShop.EShop.model.service.OrderServiceModel;
import com.EShop.EShop.repository.OrderRepository;
import com.EShop.EShop.repository.ProductRepository;
import com.EShop.EShop.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.EShop.EShop.constants.ValidationMessage.ORDER_NOT_FOUND_EX_MSG;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createOrder(OrderServiceModel orderServiceModel) {
        orderServiceModel.setIssuedOn(LocalDateTime.now());

        Order order = this.modelMapper.map(orderServiceModel, Order.class);
        order.setShippingAddress(orderServiceModel.getCustomer().getAddress());
        order.setStatus(StatusEnums.PENDING);
        this.orderRepository.save(order);

    }

    @Override
    public void changeOrderStatus(Long id) {

        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EX_MSG));

        order.setStatusDate(LocalDateTime.now());
        changeStatus(order);
        this.orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel findOrderById(Long id) {
        return this.orderRepository.findById(id)
                .map(order -> this.modelMapper.map(order, OrderServiceModel.class))
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EX_MSG));
    }

    @Override
    public List<OrderServiceModel> findOrdersByCustomer(String customerName) {
        return this.orderRepository.findAllOrdersByCustomer_UsernameOrderByIssuedOn(customerName)
                .stream().map(order -> modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findOrdersByCustomerAndStatus(String customerName, StatusEnums statusEnums) {
        return this.orderRepository.findAllOrdersByCustomerUsernameAndStatus_OrderByIssuedOn(customerName,statusEnums)
                .stream()
                .map(order -> modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findOrdersByStatus(StatusEnums statusEnums) {
        return this.orderRepository.findAllOrdersByStatus_OrderByIssuedOn(statusEnums)
                .stream()
                .map(order -> modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toList());

    }

    private void changeStatus(Order order) {
        order.setStatus(StatusEnums.values()[Arrays.asList(StatusEnums.values()).indexOf(order.getStatus()) + 1]);
    }
}
