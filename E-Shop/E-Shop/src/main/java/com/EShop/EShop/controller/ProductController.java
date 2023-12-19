package com.EShop.EShop.controller;


import com.EShop.EShop.model.binding.ProductAddBindingModel;
import com.EShop.EShop.model.service.CategoryServiceModel;
import com.EShop.EShop.model.service.ProductServiceModel;
import com.EShop.EShop.model.view.CategoryViewModel;
import com.EShop.EShop.model.view.ProductAllViewModel;
import com.EShop.EShop.model.view.ProductDetailsViewModel;
import com.EShop.EShop.service.CategoryService;
import com.EShop.EShop.service.ProductService;
import com.EShop.EShop.web.anotacions.interceptors.PageTitle;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;


    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Products")
    public ModelAndView addProduct(@ModelAttribute(name = "model") ProductAddBindingModel model,
                                   ModelAndView modelAndView) {

        return loadAndReturnModelAndView(model, modelAndView);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Products")
    public ModelAndView allProducts(ModelAndView modelAndView) {

        List<ProductAllViewModel> productAllViewModels =
                mapProductServiceToViewModel(productService.findAllFilteredProducts());
        modelAndView.addObject("products", productAllViewModels);

        return view("product/all-product", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Product Details")
    public ModelAndView detailsProduct(@PathVariable Long id, ModelAndView modelAndView) {

        ProductDetailsViewModel product =
                modelMapper.map(productService.findProductById(id), ProductDetailsViewModel.class);
        modelAndView.addObject("product", product);
        return view("product/details", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Product")
    public ModelAndView editProduct(@PathVariable Long id, ModelAndView modelAndView,
                                    @ModelAttribute("model") ProductAddBindingModel productAddBindingModel) {
        productAddBindingModel = this.modelMapper.map(productService.findProductById(id), ProductAddBindingModel.class);
        List<CategoryViewModel> categoryViewModelList =
                mapCategoryServiceToViewModel(categoryService.findAllFilteredCategories());
        modelAndView.addObject("model", productAddBindingModel);
        modelAndView.addObject("categories", categoryViewModelList);
        modelAndView.addObject("productId", id);
        return view("product/edit-product", modelAndView);

    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(ModelAndView modelAndView, @PathVariable Long id,
                                           @Valid @ModelAttribute("model") ProductAddBindingModel model,
                                           BindingResult bindingResult) throws IOException {
        boolean isNewImageUploaded = !model.getImage().isEmpty();
        initImage(model);
        ProductServiceModel productServiceModel = modelMapper.map(model, ProductServiceModel.class);
        productService.editProduct(id, productServiceModel, isNewImageUploaded, model.getImage());
        if (bindingResult.hasErrors() ||
                productService.editProduct(id, productServiceModel, isNewImageUploaded, model.getImage()) == null) {

            modelAndView.addObject("categories", mapCategoryServiceToViewModel
                    (categoryService.findAllFilteredCategories()));
            modelAndView.addObject("model", model);
            modelAndView.addObject("productId", id);
            return this.view("product/edit-product", modelAndView);
        }
        return redirect("/product/details/" + id);
    }

    private void initImage(ProductAddBindingModel model) {
        if (model.getImage().isEmpty()) {
            MultipartFile multipartFile = new MultipartFile() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public String getOriginalFilename() {
                    return null;
                }

                @Override
                public String getContentType() {
                    return null;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public long getSize() {
                    return 0;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return new byte[0];
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return null;
                }

                @Override
                public void transferTo(File file) throws IOException, IllegalStateException {

                }
            };
            model.setImage(multipartFile);
        }

    }

    public ModelAndView deleteProduct(@PathVariable Long id, ModelAndView modelAndView,
                                      @ModelAttribute("model") ProductAddBindingModel productAddBindingModel) {
        productAddBindingModel = this.modelMapper.map(productService.findProductById(id), ProductAddBindingModel.class);
        modelAndView.addObject("model", productAddBindingModel);
        modelAndView.addObject("categories",
                mapCategoryServiceToViewModel(categoryService.findAllFilteredCategories()));
        modelAndView.addObject("productId", id);
        return view("product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProductConfirm(@PathVariable Long id) {
        productService.deleteProduct(id);
        return redirect("/product/all");

    }

    @GetMapping("/category/{category}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView fetchByCategory(@PathVariable String category, ModelAndView modelAndView) {

        List<ProductAllViewModel> products = new ArrayList<>();
        products = category.equals("All") ? mapProductServiceToViewModel(productService.findAllFilteredProducts())
                : mapProductServiceToViewModel(productService.findAllByCategoryFilteredProducts(category));
        modelAndView.addObject("categoryName", category);
        modelAndView.addObject("products", products);
        return view("product/show-product", modelAndView);
    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<ProductAllViewModel> fetchAllProducts(){
        return mapProductServiceToViewModel(productService.findAllFilteredProducts());

    }
    private List<ProductAllViewModel> mapProductServiceToViewModel(List<ProductServiceModel> productServiceModels) {
        return productServiceModels.stream()
                .map(product -> modelMapper.map(product, ProductAllViewModel.class))
                .collect(Collectors.toList());

    }

    private ModelAndView loadAndReturnModelAndView(ProductAddBindingModel productAddBindingModel, ModelAndView modelAndView) {
        List<CategoryViewModel> categories = mapCategoryServiceToViewModel(categoryService.findAllFilteredCategories());
        modelAndView.addObject("model", productAddBindingModel);
        modelAndView.addObject("categories", categories);
        return view("product/add-product", modelAndView);

    }

    private List<CategoryViewModel> mapCategoryServiceToViewModel(List<CategoryServiceModel> categoryServiceModels) {
        return categoryServiceModels.stream()
                .map(product -> modelMapper.map(product, CategoryViewModel.class))
                .collect(Collectors.toList());
    }


}
