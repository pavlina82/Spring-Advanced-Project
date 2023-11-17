package com.EShop.EShop.service.impl;

import com.EShop.EShop.exception.OrderNotFoundException;
import com.EShop.EShop.model.entity.Order;
import com.EShop.EShop.model.entity.Receipt;
import com.EShop.EShop.model.entity.User;
import com.EShop.EShop.model.service.ReceiptServiceModel;
import com.EShop.EShop.repository.OrderRepository;
import com.EShop.EShop.repository.ReceiptRepository;
import com.EShop.EShop.repository.UserRepository;
import com.EShop.EShop.service.OrderService;
import com.EShop.EShop.service.ReceiptService;
import com.EShop.EShop.validation.ReceiptValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.EShop.EShop.constants.ValidationMessage.ORDER_NOT_FOUND_EX_MSG;
import static com.EShop.EShop.constants.ValidationMessage.USER_NOT_FOUND_EX_MSG;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ReceiptValidationService receiptValidationService;
    private final ModelMapper modelMapper;


    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository, UserRepository userRepository, OrderRepository orderRepository, OrderService orderService, ReceiptValidationService receiptValidationService, ModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.receiptValidationService = receiptValidationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void receiptRegister(ReceiptServiceModel receiptServiceModel) {
        if (!receiptValidationService.isValid(receiptServiceModel)) {
            throw new IllegalArgumentException();
        }
        Receipt receipt = this.modelMapper.map(receiptServiceModel, Receipt.class);
        this.receiptRepository.save(receipt);

    }

    @Override
    public void crateReceipt(Long id, String name) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EX_MSG));

        User user = this.userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_EX_MSG));

        Receipt receipt = new Receipt();
        receipt.setFee(order.getTotalPrice());
        receipt.setIssuedOn(LocalDateTime.now());
        receipt.setOrder(order);
        receipt.setRecipient(user);

        this.receiptRepository.save(receipt);
        this.orderService.changeOrderStatus(id);

    }
}
