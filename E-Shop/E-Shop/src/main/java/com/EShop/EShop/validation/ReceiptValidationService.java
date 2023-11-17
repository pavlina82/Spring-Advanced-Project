package com.EShop.EShop.validation;

import com.EShop.EShop.model.service.ReceiptServiceModel;

public interface ReceiptValidationService {
    boolean isValid(ReceiptServiceModel receiptServiceModel);
}
