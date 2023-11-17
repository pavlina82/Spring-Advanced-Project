package com.EShop.EShop.Controller;


import com.EShop.EShop.model.binding.ProductAddBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/add")
    public ModelAndView addProduct(ProductAddBindingModel model){

        return new ModelAndView("/products/add");
    }

    @GetMapping("/all")
    public ModelAndView allProducts(){

        return new ModelAndView("product/all-products");
    }


}
