package com.EShop.EShop.util.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.EShop.EShop.constants.ValidationMessage.PRODUCT_NAME_EXIST_EX_MSG;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = PRODUCT_NAME_EXIST_EX_MSG)
public class ProductNameAlreadyExistsException extends RuntimeException{

    private int statusCode;

    public ProductNameAlreadyExistsException() {
        this.statusCode = 409;
    }

    public ProductNameAlreadyExistsException(String message) {
        super(message);
        this.statusCode = 409;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
