package com.EShop.EShop.service;


import com.EShop.EShop.model.service.ProductServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductServiceModel createProduct(ProductServiceModel productServiceModel,
                                      MultipartFile image) throws IOException;

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(Long id);

    List<ProductServiceModel> findProductsByPartOfName(String name);

    ProductServiceModel editProduct(Long id, ProductServiceModel productServiceModel,
                                    boolean isNewImageUploaded, MultipartFile image) throws IOException;

    void deleteProduct(Long id);

    List<ProductServiceModel> findAllByCategory(String category);

    List<ProductServiceModel> findAllFilteredProducts();

    List<ProductServiceModel> findAllByCategoryFilteredProducts(String category);
}





