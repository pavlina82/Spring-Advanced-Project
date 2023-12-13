package com.EShop.EShop.validation.impl;

import com.EShop.EShop.model.service.UserServiceModel;
import com.EShop.EShop.validation.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(UserServiceModel user) {
        return user != null;
    }
}
