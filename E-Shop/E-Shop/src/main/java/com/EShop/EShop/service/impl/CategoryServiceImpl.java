package com.EShop.EShop.service.impl;

import com.EShop.EShop.exception.CategoryNotFoundException;
import com.EShop.EShop.model.entity.Category;
import com.EShop.EShop.model.service.CategoryServiceModel;
import com.EShop.EShop.repository.CategoryRepository;
import com.EShop.EShop.service.CategoryService;


import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static com.EShop.EShop.constants.ValidationMessage.CATEGORY_NOT_FOUND_EX_MSG;

;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Validator validator) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {

        if (!validator.validate(categoryServiceModel).isEmpty()) {
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND_EX_MSG);
        }

        Category category = this.modelMapper.map(categoryServiceModel, Category.class);

        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category)
                , CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream().filter(Category::isDeleted)
                .map(category -> this.modelMapper.map(category, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategoryById(Long id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow();

        if (category.isDeleted()) {
            throw new CategoryNotFoundException(CATEGORY_NOT_FOUND_EX_MSG);
        }
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel editCategory(Long id, CategoryServiceModel categoryServiceModel) {

        Category category = this.categoryRepository.findById(id)
                .orElseThrow();
        category.setName(categoryServiceModel.getName());
        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category),
                CategoryServiceModel.class);


    }

    @Override
    public void deleteCategory(Long id){

        Category category = this.categoryRepository.findById(id)
                .orElseThrow();

        category.setDeleted(true);
        this.categoryRepository.save(category);

        this.modelMapper.map(category, CategoryServiceModel.class);
    }
    @Override
    public List<CategoryServiceModel> findAllFilteredCategories() {
        return findAllCategories().stream()
                .filter(c->!c.isDeleted())
                .collect(Collectors.toList());
    }
}
