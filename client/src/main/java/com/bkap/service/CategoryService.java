package com.bkap.service;

import com.bkap.dto.CategoryDTO;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 18/08/2020 - 14:24
 * @created_by Tung lam
 * @since 18/08/2020
 */
public interface CategoryService {
    List<CategoryDTO> getAllCategory();

    CategoryDTO getCategoryById(Long id);
}
