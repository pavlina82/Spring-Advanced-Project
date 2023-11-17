package com.EShop.EShop.Controller;


import com.EShop.EShop.model.binding.CategoryAddBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping("/add")
    public ModelAndView addCategory(CategoryAddBindingModel categoryAddBindingModel){

        return new ModelAndView();
    }

    @GetMapping("/all")
    public ModelAndView allCategories(){

        return new ModelAndView("category/all-categories");
    }

    @GetMapping("/delete")
    public ModelAndView deleteCategory(){

        return new ModelAndView("category/delete-category");
    }


}
