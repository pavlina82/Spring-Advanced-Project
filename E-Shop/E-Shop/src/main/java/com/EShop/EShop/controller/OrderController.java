package com.EShop.EShop.controller;

import com.EShop.EShop.exception.OrderNotFoundException;
import com.EShop.EShop.model.enums.StatusEnums;
import com.EShop.EShop.model.service.OrderProductServiceModel;
import com.EShop.EShop.model.service.OrderServiceModel;
import com.EShop.EShop.model.view.OrderDetailsViewModel;
import com.EShop.EShop.model.view.OrderViewModel;
import com.EShop.EShop.model.view.ShoppingCartItem;
import com.EShop.EShop.service.OrderService;
import com.EShop.EShop.web.anotacions.interceptors.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.EShop.EShop.constants.ValidationMessage.*;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("orders")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {

        List<OrderViewModel> viewModel = mapListOrderServiceToViewModel(orderService.findAllOrders());
        modelAndView.addObject("orders", viewModel);

        return view("order/all-orders", modelAndView);
    }

    @GetMapping("/all/details/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allOrderDetails(@PathVariable Long id, ModelAndView modelAndView) {

        OrderDetailsViewModel order = loadOrderDetailsViewModel(id);
        modelAndView.addObject("order", order);
        return view("order/order-products", modelAndView);
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("My orders")
    public ModelAndView getMyOrders(ModelAndView modelAndView, Principal principal) {
        String customerName = principal.getName();

        List<OrderViewModel> myOrders = mapListOrderServiceToViewModel(orderService.findOrdersByCustomer(customerName));
        List<OrderViewModel> myPendingOrders = mapListOrderServiceToViewModel(orderService.findOrdersByCustomerAndStatus(customerName, StatusEnums.PENDING));
        List<OrderViewModel> myShippedOrders = mapListOrderServiceToViewModel(orderService.findOrdersByCustomerAndStatus(customerName, StatusEnums.SHIPPED));
        List<OrderViewModel> myDeliveredOrders = mapListOrderServiceToViewModel(orderService.findOrdersByCustomerAndStatus(customerName, StatusEnums.DELIVERED));

        modelAndView.addObject("orders", myOrders);
        modelAndView.addObject("myPendingOrders", myPendingOrders);
        modelAndView.addObject("myShippedOrders", myShippedOrders);
        modelAndView.addObject("myDeliveredOrders", myDeliveredOrders);

        return view("order/my-orders", modelAndView);

    }

    @GetMapping("/my/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Order Details")
    public ModelAndView myOrderDetails(@PathVariable Long id, ModelAndView modelAndView) {
        OrderDetailsViewModel order = loadOrderDetailsViewModel(id);
        modelAndView.addObject("order", order);

        return view("order/order-details", modelAndView);

    }

    @GetMapping("/fetch/{status}")
    @ResponseBody
    public List<OrderViewModel> fetchByCategory(@PathVariable String status){

        return loadOrdersByStatus(status);
    }

    private List<OrderViewModel> loadOrdersByStatus(String status) {

        StatusEnums statusEnums = StatusEnums.PENDING;

        switch (status){
            case STATUS_ALL:
                return mapListOrderServiceToViewModel(orderService.findAllOrders());
            case STATUS_SHIPPED:
                statusEnums = StatusEnums.SHIPPED;
                break;
            case STATUS_DELIVERED:
                statusEnums = StatusEnums.DELIVERED;
                break;
            case STATUS_ACQUIRED:
                statusEnums = StatusEnums.ACQUIRED;
                break;
        }
        return mapListOrderServiceToViewModel(orderService.findOrdersByStatus(statusEnums));
    }

    private OrderDetailsViewModel loadOrderDetailsViewModel(Long id) {
        OrderServiceModel orderServiceModel = orderService.findOrderById(id);
        List<OrderProductServiceModel> products = orderServiceModel.getProducts();

        OrderDetailsViewModel order = modelMapper.map(orderServiceModel, OrderDetailsViewModel.class);
        List<ShoppingCartItem> items = new ArrayList<>();

        Map<OrderProductServiceModel, Integer> productItems = new HashMap<>();

        for (OrderProductServiceModel product : products) {
            productItems.putIfAbsent(product, 0);
            int quantity = productItems.get(product) + 1;
            productItems.put(product, quantity);
        }

        for (Map.Entry<OrderProductServiceModel, Integer> productKVP : productItems.entrySet()) {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

            shoppingCartItem.setQuantity(productKVP.getValue());
            OrderProductServiceModel orderProductServiceModel =
                    modelMapper.map(productKVP.getKey(), OrderProductServiceModel.class);
            shoppingCartItem.setProduct(orderProductServiceModel);
            items.add(shoppingCartItem);
        }
        order.setItems(items);
        return order;
    }

    private List<OrderViewModel> mapListOrderServiceToViewModel(List<OrderServiceModel> orderServiceModels) {

        return orderServiceModels.stream()
                .map(order -> modelMapper.map(order, OrderViewModel.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ModelAndView handleProductNotFound(OrderNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message",e.getMessage());
        modelAndView.addObject("statusCode",e.getStatusCode());

        return modelAndView;

    }
}
