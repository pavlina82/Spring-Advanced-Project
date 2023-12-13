package com.EShop.EShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.EShop.EShop.constants.ValidationMessage.RECEIPT_NAME_EXIST_EX_MSG;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = RECEIPT_NAME_EXIST_EX_MSG)
public class ReceiptNotFoundException extends RuntimeException{

    private int statusCode;

    public ReceiptNotFoundException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
