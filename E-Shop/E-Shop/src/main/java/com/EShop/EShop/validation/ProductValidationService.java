package com.EShop.EShop.validation;

import com.EShop.EShop.model.entity.Product;
import com.EShop.EShop.model.service.ProductServiceModel;

public interface ProductValidationService {
    boolean isValid(Product product);

    boolean isValid(ProductServiceModel productServiceModel);
}
