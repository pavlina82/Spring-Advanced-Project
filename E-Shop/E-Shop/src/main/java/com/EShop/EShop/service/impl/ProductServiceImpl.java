package com.EShop.EShop.service.impl;

import com.EShop.EShop.exception.ProductNameAlreadyExistsException;
import com.EShop.EShop.exception.ProductNotFoundException;
import com.EShop.EShop.model.entity.Product;
import com.EShop.EShop.model.service.CategoryServiceModel;
import com.EShop.EShop.model.service.ProductServiceModel;
import com.EShop.EShop.repository.OfferRepository;
import com.EShop.EShop.repository.ProductRepository;
import com.EShop.EShop.service.CategoryService;
import com.EShop.EShop.service.FileUpload;
import com.EShop.EShop.service.ProductService;
import com.EShop.EShop.validation.ProductValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.EShop.EShop.constants.ValidationMessage.PRODUCT_ID_DOESNT_EXIST_EX_MSG;
import static com.EShop.EShop.constants.ValidationMessage.PRODUCT_NAME_EXIST_EX_MSG;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;
    private final CategoryService categoryService;
    private final FileUpload fileUpload;
    private final ProductValidationService productValidation;
    private final ModelMapper modelMapper;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, OfferRepository offerRepository, CategoryService categoryService, FileUpload fileUpload, ProductValidationService productValidation, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.categoryService = categoryService;
        this.fileUpload = fileUpload;
        this.productValidation = productValidation;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel, MultipartFile image) throws IOException {

        if (!productValidation.isValid(productServiceModel) || image.isEmpty()) {
            throw new IllegalArgumentException("Invalid product.");

        }
        if (productRepository.findByName(productServiceModel.getName())
                .orElse(null) != null) {
            throw new ProductNameAlreadyExistsException(PRODUCT_NAME_EXIST_EX_MSG);

        }
        Product product = this.modelMapper.map(productServiceModel, Product.class);

        product.setImage_url(
                this.fileUpload.uploadFile(image)
        );
        product = this.productRepository.saveAndFlush(product);

        if (product == null) {
            throw new IllegalArgumentException("Invalid product.");
        }
        return this.modelMapper.map(product, ProductServiceModel.class);

    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        List<Product> products = this.productRepository.findAll();

        return products.stream()
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(Long id) {
        return this.productRepository.findById(id)
                .map(product -> {
                    ProductServiceModel productServiceModel = this.modelMapper.map(product, ProductServiceModel.class);
                    this.offerRepository.findByProductId(productServiceModel.getId())
                            .ifPresent(offer -> productServiceModel.setDiscountedPrice(offer.getPrice()));
                    return productServiceModel;
                }).orElseThrow(() -> new ProductNotFoundException("Product with the given id was not found!"));
    }

    @Override
    public List<ProductServiceModel> findProductsByPartOfName(String name) {
        return findAllFilteredProducts()
                .stream()
                .filter(productServiceModel -> productServiceModel.getName()
                        .toLowerCase().contains(name.toLowerCase()))
                .map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel editProduct(Long id, ProductServiceModel productServiceModel, boolean isNewImageUploaded, MultipartFile image) throws IOException {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with the given id was not found!"));
        if (!productValidation.isValid(productServiceModel)) {
            throw new IllegalArgumentException("Invalid product.");
        }
        productServiceModel.setId(id);
        Product update = modelMapper.map(productServiceModel, Product.class);
        if (product == null || update == null) {
            throw new ProductNotFoundException("Product with the given id was not found!");
        }

        if (isNewImageUploaded){
            update.setImage_url(
                    this.fileUpload.uploadFile(image)
            );
        }else {
            update.setImage_url(product.getImage_url());
        }
        this.offerRepository.findByProductId(product.getId())
                .ifPresent(offer -> {
                    offer.setPrice(product.getPrice().multiply(new BigDecimal("0.75")));
                    this.offerRepository.save(offer);
                });
        return modelMapper.map(this.productRepository.saveAndFlush(update), ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_ID_DOESNT_EXIST_EX_MSG));
        product.setDeleted(true);

        this.productRepository.save(product);
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String category) {
        List<String> categories = this.categoryService.findAllCategories()
                .stream().map(CategoryServiceModel::getName).toList();
        if (!categories.contains(category)){
            throw new SecurityException("Page Not Found: ERROR 404!\n" +
                    "This page doesn't exist...");
        }
        return this.productRepository.findAll()
                .stream()
                .filter(product -> product.getCategories().stream()
                        .anyMatch(category1 -> category1.getName().equals(category)))
                .map(product -> modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findAllFilteredProducts() {
        return findAllProducts()
                .stream()
                .filter(p -> !p.isDeleted())
                .filter(p -> p.getCategories().stream().anyMatch(c -> !c.isDeleted()))
                .map(p -> {
                    ProductServiceModel productServiceModel = modelMapper.map(p, ProductServiceModel.class);
                    offerRepository.findByProductId(productServiceModel.getId())
                            .ifPresent(offer -> productServiceModel.setDiscountedPrice(offer.getPrice()));
                    return productServiceModel;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findAllByCategoryFilteredProducts(String category) {
        return findAllByCategory(category)
                .stream()
                .filter(productServiceModel -> !productServiceModel.isDeleted())
                .filter(productServiceModel -> productServiceModel.getCategories().stream().anyMatch(categoryServiceModel ->
                        !categoryServiceModel.isDeleted()))
                .map(productServiceModel -> {
                    ProductServiceModel productServiceModel1 = modelMapper.map(productServiceModel, ProductServiceModel.class);
                    offerRepository.findByProductId(productServiceModel1.getId())
                            .ifPresent(o -> productServiceModel1.setDiscountedPrice(o.getPrice()));

                    return productServiceModel1;
                }).collect(Collectors.toList());
    }


}
