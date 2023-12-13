package com.EShop.EShop.validation.impl;

import com.EShop.EShop.model.entity.Receipt;
import com.EShop.EShop.model.service.ReceiptServiceModel;
import com.EShop.EShop.validation.ReceiptValidationService;
import org.springframework.stereotype.Component;

@Component
public class ReceiptValidationServiceImpl implements ReceiptValidationService {

    @Override
    public boolean isValid(Receipt receipt) {
        return receipt != null;
    }

    @Override
    public boolean isValid(ReceiptServiceModel receiptServiceModel) {
        return receiptServiceModel != null;
    }
}
