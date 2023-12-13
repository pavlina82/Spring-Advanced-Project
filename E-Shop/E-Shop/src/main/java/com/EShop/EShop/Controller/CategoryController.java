package com.EShop.EShop.controller;


import com.EShop.EShop.exception.CategoryNotFoundException;
import com.EShop.EShop.model.binding.CategoryAddBindingModel;
import com.EShop.EShop.model.service.CategoryServiceModel;
import com.EShop.EShop.model.view.CategoryViewModel;
import com.EShop.EShop.service.CategoryService;


import com.EShop.EShop.web.anotacions.PageTitle;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;


    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PageTitle("Add Category")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategory(@ModelAttribute(name = "model") CategoryAddBindingModel categoryAddBindingModel
            , ModelAndView modelAndView) {

        return loadAndReturnModelAndView(categoryAddBindingModel, modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(@Valid @ModelAttribute(name = "model") CategoryAddBindingModel categoryAddBindingModel,
                                           BindingResult bindingResult, ModelAndView modelAndView) {

        CategoryServiceModel categoryServiceModel = modelMapper
                .map(categoryAddBindingModel, CategoryServiceModel.class);

        if (bindingResult.hasErrors() || categoryService.addCategory(categoryServiceModel) == null) {

            return loadAndReturnModelAndView(categoryAddBindingModel, modelAndView);
        }
        return redirect("/categories/all");
    }

    @GetMapping("/all")
    public ModelAndView allCategories(ModelAndView modelAndView) {

        List<CategoryViewModel> categories =
                mapCategoryServiceToViewModel(categoryService.findAllFilteredCategories());

        modelAndView.addObject("categories", categories);

        return view("category/all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Category")
    public ModelAndView editCategory(@PathVariable Long id, ModelAndView modelAndView) {
        CategoryViewModel categoryViewModel =
                modelMapper.map(categoryService.findCategoryById(id), CategoryViewModel.class);
        modelAndView.addObject("model", categoryViewModel);

        return view("category/edit-category", modelAndView);


    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategoryConfirm(@PathVariable Long id, @Valid @ModelAttribute(name = "model")
    CategoryAddBindingModel categoryAddBindingModel,
                                            BindingResult bindingResult, ModelAndView modelAndView) {
        CategoryServiceModel categoryServiceModel =
                modelMapper.map("model", CategoryServiceModel.class);
        if (bindingResult.hasErrors() || categoryService.editCategory(id, categoryServiceModel) == null) {
            modelAndView.addObject("model", categoryAddBindingModel);
            return view("category/edit-category", modelAndView);
        }

        return redirect("/categories/all");
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Delete Category")
    public ModelAndView deleteCategory(@PathVariable Long id, ModelAndView modelAndView) {

        CategoryViewModel categoryViewModel =
                modelMapper.map(categoryService.findCategoryById(id), CategoryViewModel.class);
        modelAndView.addObject("model", categoryViewModel);

        return view("category/delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategoryConfirm(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return redirect("/categories/all");

    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories() {

        List<CategoryViewModel> categories =
                mapCategoryServiceToViewModel(categoryService.findAllFilteredCategories());
        return categories;

    }

    private List<CategoryViewModel> mapCategoryServiceToViewModel(List<CategoryServiceModel> categoryServiceModels) {
        return categoryServiceModels.stream()
                .map(product -> modelMapper.map(product, CategoryViewModel.class))
                .collect(Collectors.toList());
    }

    private ModelAndView loadAndReturnModelAndView(CategoryAddBindingModel categoryAddBindingModel, ModelAndView modelAndView) {

        modelAndView.addObject("model", categoryAddBindingModel);
        return view("category/add-category", modelAndView);
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    public ModelAndView handleProductNotFound(CategoryNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }


}
