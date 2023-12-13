package com.EShop.EShop.service.impl;

import com.EShop.EShop.exception.OrderNotFoundException;
import com.EShop.EShop.exception.ReceiptNotFoundException;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.EShop.EShop.constants.ValidationMessage.*;

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
    public List<ReceiptServiceModel> findAllReceiptsByUsername(String username) {
        return this.receiptRepository.findAllReceiptsByRecipient_UsernameOrderByIssuedOn(username)
                .stream()
                .map(receipt -> this.modelMapper.map(receipt, ReceiptServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<ReceiptServiceModel> findAllReceipts() {
        return this.receiptRepository.findAll()
                .stream()
                .map(receipt -> this.modelMapper.map(receipt, ReceiptServiceModel.class))
                .collect(Collectors.toList());
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
    public void createReceipt(Long orderId, String name) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow();
        User user = this.userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        Receipt receipt = new Receipt();
        receipt.setFee(order.getTotalPrice());
        receipt.setIssuedOn(LocalDateTime.now());
        receipt.setOrder(order);
        receipt.setRecipient(receipt.getRecipient());

        this.receiptRepository.save(receipt);
        this.orderService.changeOrderStatus(orderId);
    }

    @Override
    public ReceiptServiceModel getReceiptById(Long id) {
        Receipt receipt = this.receiptRepository.findById(id)
                .orElseThrow(() -> new ReceiptNotFoundException(RECEIPT_NAME_EXIST_EX_MSG));
        return modelMapper.map(receipt, ReceiptServiceModel.class);

    }

    @Override
    public ReceiptServiceModel findReceiptById(Long receiptId) {
        Receipt receipt = this.receiptRepository.findById(receiptId)
                .orElseThrow();

        return modelMapper.map(receipt, ReceiptServiceModel.class);
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
