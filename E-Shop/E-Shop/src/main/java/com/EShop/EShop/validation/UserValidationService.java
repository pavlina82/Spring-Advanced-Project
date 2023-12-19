package com.EShop.EShop.validation;

import com.EShop.EShop.model.service.UserServiceModel;

public interface UserValidationService {
    boolean isValid(UserServiceModel user);
}
