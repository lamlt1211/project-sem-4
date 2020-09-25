package com.bkap.convert;

import com.bkap.dto.CategoryDTO;
import com.bkap.entity.Category;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:46
 * @created_by Tung lam
 * @since 22/07/2020
 */
public class CategoryConvert {
    public static CategoryDTO EntityToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setCreatedAt(category.getCreatedAt());
        categoryDTO.setUpdatedAt(category.getUpdatedAt());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }

    public static Category DTOtoEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setCreatedAt(categoryDTO.getCreatedAt());
        category.setUpdatedAt(categoryDTO.getUpdatedAt());
        return category;
    }
}
