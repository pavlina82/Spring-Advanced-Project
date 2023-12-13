package com.EShop.EShop.validation;

import com.EShop.EShop.model.entity.Receipt;
import com.EShop.EShop.model.service.ReceiptServiceModel;

public interface ReceiptValidationService {
    boolean isValid(Receipt receipt);

    boolean isValid(ReceiptServiceModel receiptServiceModel);
}
