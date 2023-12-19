package com.EShop.EShop.service;


import com.EShop.EShop.model.service.CategoryServiceModel;

import java.util.List;


public interface CategoryService {
    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findCategoryById(Long id);

    CategoryServiceModel editCategory(Long id, CategoryServiceModel categoryServiceModel);

    void deleteCategory(Long id);

    List<CategoryServiceModel> findAllFilteredCategories();
}
