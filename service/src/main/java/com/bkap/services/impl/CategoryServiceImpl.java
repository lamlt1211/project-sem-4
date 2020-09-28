package com.bkap.services.impl;

import com.bkap.convert.CategoryConvert;
import com.bkap.dto.CategoryDTO;
import com.bkap.entity.Category;
import com.bkap.exceptions.NotFoundException;
import com.bkap.repositories.CategoryRepository;
import com.bkap.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:57
 * @created_by Tung lam
 * @since 22/07/2020
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            throw new NotFoundException("Empty.category");
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDTO categoryDTO = CategoryConvert.EntityToDTO(category);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    @Override
    public List<CategoryDTO> getAllCategoryStatusActive() {
        List<Category> categoryList = categoryRepository.getAllCategoryStatusActive();
        if (categoryList.isEmpty()) {
            throw new NotFoundException("Empty.category");
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDTO categoryDTO = CategoryConvert.EntityToDTO(category);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    @Override
    public List<CategoryDTO> getAllCategoryStatusUnActive() {
        List<Category> categoryList = categoryRepository.getAllCategoryStatusUnActive();
        if (categoryList.isEmpty()) {
            throw new NotFoundException("Empty.category");
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDTO categoryDTO = CategoryConvert.EntityToDTO(category);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    @Override
    public CategoryDTO getCateById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            CategoryDTO categoryDTO = CategoryConvert.EntityToDTO(categoryOptional.get());
            return categoryDTO;
        }
        throw new NotFoundException("NotFound.id");
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category check = categoryRepository.findByName(categoryDTO.getName());
        if (check == null) {
            Category category = CategoryConvert.DTOtoEntity(categoryDTO);
            category.setStatus(1);
            return CategoryConvert.EntityToDTO(categoryRepository.save(category));
        }
        return null;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = CategoryConvert.DTOtoEntity(categoryDTO);
            category.setId(id);
            return CategoryConvert.EntityToDTO(categoryRepository.save(category));
        }
        throw new NotFoundException("NotFound.id");
    }

    // Chuyển trạng thái category , active  || unactive
    @Override
    public CategoryDTO deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            if (categoryOptional.get().getStatus() == 1) {
                categoryOptional.get().setStatus(0);
                return CategoryConvert.EntityToDTO(categoryRepository.save(categoryOptional.get()));
            }
            if (categoryOptional.get().getStatus() == 0) {
                categoryOptional.get().setStatus(1);
                return CategoryConvert.EntityToDTO(categoryRepository.save(categoryOptional.get()));
            }
        }
        throw new NotFoundException("NotFound.id");
    }

    @Override
    public Page<CategoryDTO> getCategoryByPage(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Category> pageResult = categoryRepository.findBySearchValue(searchValue, pageable);
        return pageResult.map(CategoryConvert::EntityToDTO);
    }

    public Long countCategory() {
        return categoryRepository.count();
    }
}
