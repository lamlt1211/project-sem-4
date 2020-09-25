package com.bkap.convert;

import com.bkap.dto.ProductDTO;
import com.bkap.entity.Category;
import com.bkap.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:47
 * @created_by Tung lam
 * @since 22/07/2020
 */
public class ProductConvert {
    private ProductConvert() {
    }

    public static ProductDTO EntityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImage(product.getImage());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        productDTO.setStatus(product.getStatus());
        List<Category> categories = product.getCategories();
        productDTO.setCategoryDTOList(categories.stream().map(CategoryConvert::EntityToDTO).collect(Collectors.toList()));
        return productDTO;
    }

    public static Product DTOToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setUpdatedAt(productDTO.getUpdatedAt());
        product.setStatus(productDTO.getStatus());
        return product;
    }
}
