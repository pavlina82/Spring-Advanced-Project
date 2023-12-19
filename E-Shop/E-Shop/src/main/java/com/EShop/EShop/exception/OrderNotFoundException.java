package com.EShop.EShop.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.EShop.EShop.constants.ValidationMessage.ORDER_NOT_FOUND_EX_MSG;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ORDER_NOT_FOUND_EX_MSG)
public class OrderNotFoundException extends RuntimeException{

    private int statusCode;

    public OrderNotFoundException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
