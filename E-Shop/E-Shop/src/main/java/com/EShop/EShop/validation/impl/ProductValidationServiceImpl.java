package com.EShop.EShop.validation.impl;

import com.EShop.EShop.model.entity.Product;
import com.EShop.EShop.model.service.CategoryServiceModel;
import com.EShop.EShop.model.service.ProductServiceModel;
import com.EShop.EShop.validation.ProductValidationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidationServiceImpl implements ProductValidationService {

    @Override
    public boolean isValid(Product product) {
        return product != null;
    }

    @Override
    public boolean isValid(ProductServiceModel productServiceModel) {
        return productServiceModel != null
                && areCategoriesValid(productServiceModel.getCategories());
    }

    private boolean areCategoriesValid(List<CategoryServiceModel> categories) {
        return categories != null && !categories.isEmpty();
    }
}
