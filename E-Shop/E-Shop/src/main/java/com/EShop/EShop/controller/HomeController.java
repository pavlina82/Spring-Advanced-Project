package com.EShop.EShop.controller;

import com.EShop.EShop.model.service.CategoryServiceModel;
import com.EShop.EShop.model.view.CategoryViewModel;
import com.EShop.EShop.service.CategoryService;
import com.EShop.EShop.web.anotacions.interceptors.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    @PageTitle("index")
    public ModelAndView index(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("principal", principal);
        return view("/index", modelAndView);
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("home")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {

        List<CategoryViewModel> categories = mapCategoryServiceToViewModel(categoryService.findAllFilteredCategories());
        modelAndView.addObject("principal", principal);
        modelAndView.addObject("categories", categories);

        return view("/home", modelAndView);

    }

    private List<CategoryViewModel> mapCategoryServiceToViewModel(List<CategoryServiceModel> categoryServiceModels) {
        return categoryServiceModels.stream()
                .map(product -> modelMapper.map(product, CategoryViewModel.class))
                .collect(Collectors.toList());
    }

}
