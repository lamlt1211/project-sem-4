package com.bkap.services;

import com.bkap.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:59
 * @created_by Tung lam
 * @since 22/07/2020
 */
public interface ProductService {
    List<ProductDTO> getAllProduct();

    List<ProductDTO> getAllProductByStatus();

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO, Long id);

    boolean deleteProductById(Long id);

    Page<ProductDTO> getProductByPage(String searchValue, Integer pageNo, Integer pageSize, String sortBy);


    Page<ProductDTO> getAllCategoryById(Long id, Pageable pageable);

    List<ProductDTO> getProductByCategoryId(Long id);

    List<ProductDTO> getProductByCategoryName(String name);

    Long countProduct();
}
