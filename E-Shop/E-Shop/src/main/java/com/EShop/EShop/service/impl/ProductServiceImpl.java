package com.EShop.EShop.service.impl;

import com.EShop.EShop.exception.ProductNotFoundException;
import com.EShop.EShop.model.entity.Product;
import com.EShop.EShop.repository.OfferRepository;
import com.EShop.EShop.repository.ProductRepository;
import com.EShop.EShop.service.CategoryService;
import com.EShop.EShop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.EShop.EShop.constants.ValidationMessage.PRODUCT_ID_DOESNT_EXIST_EX_MSG;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, OfferRepository offerRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void deleteProduct(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_ID_DOESNT_EXIST_EX_MSG));
        product.setDeleted(true);

        this.productRepository.save(product);
    }
}
