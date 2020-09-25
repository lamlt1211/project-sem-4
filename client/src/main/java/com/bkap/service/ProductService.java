package com.bkap.service;

import com.bkap.dto.APIResponse;
import com.bkap.dto.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 18/08/2020 - 14:24
 * @created_by Tung lam
 * @since 18/08/2020
 */
public interface ProductService {
    ProductDTO findById(Long id);

    List<ProductDTO> getAllProduct();

    APIResponse<List<ProductDTO>> getAllPageProduct(Pageable pageable);

    APIResponse<List<ProductDTO>> getProductByCategoryId(Long id, Pageable pageable);

    ProductDTO getProductById(Long id);

    List<ProductDTO> getProductByCategoryName(String categoryName);
}
