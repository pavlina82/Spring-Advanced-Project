package com.EShop.EShop.service;

import com.EShop.EShop.model.service.ReceiptServiceModel;

public interface ReceiptService {
    void receiptRegister(ReceiptServiceModel receiptServiceModel);

    void crateReceipt(Long id, String name);
}
