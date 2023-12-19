package com.EShop.EShop.service;

import com.EShop.EShop.model.service.ReceiptServiceModel;

import java.util.List;

public interface ReceiptService {
    List<ReceiptServiceModel> findAllReceiptsByUsername(String username);

    List<ReceiptServiceModel> findAllReceipts();

    void receiptRegister(ReceiptServiceModel receiptServiceModel);

    void createReceipt(Long orderId, String name);

    ReceiptServiceModel getReceiptById(Long id);

    ReceiptServiceModel findReceiptById(Long receiptId);

    void crateReceipt(Long id, String name);
}
