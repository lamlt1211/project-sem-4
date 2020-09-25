package com.bkap.services.impl;

import com.bkap.convert.ProductConvert;
import com.bkap.dto.ProductDTO;
import com.bkap.entity.Category;
import com.bkap.entity.Product;
import com.bkap.entity.Status;
import com.bkap.exceptions.NotFoundException;
import com.bkap.repositories.CategoryRepository;
import com.bkap.repositories.ProductRepository;
import com.bkap.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:57
 * @created_by Tung lam
 * @since 22/07/2020
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new NotFoundException("Empty.category");
        }
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = ProductConvert.EntityToDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAllProductByStatus() {
        List<Product> productList = productRepository.findAllProductByStatus();
        if (productList.isEmpty()) {
            throw new NotFoundException("Empty.category");
        }
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = ProductConvert.EntityToDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            ProductDTO productDTO = ProductConvert.EntityToDTO(productOptional.get());
            return productDTO;
        }
        throw new NotFoundException("NotFound.id");
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product check = productRepository.findByName(productDTO.getName());
        if (check != null) {
            return null;
        }
        Product product = ProductConvert.DTOToEntity(productDTO);
        Long[] a = productDTO.getCategoryIds();
        List<Long> list = Arrays.stream(productDTO.getCategoryIds()).collect(Collectors.toList());
        List<Category> categories = categoryRepository.findAllById(list);
        product.setCategories(categories);
        product.setStatus(Status.ACTIVE.getValue());
        return ProductConvert.EntityToDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        List<Long> listId = Arrays.stream(productDTO.getCategoryIds()).collect(Collectors.toList());
        List<Category> categoryDTOS = categoryRepository.findAllById(listId);
        if (productOptional.isPresent()) {
            Product product = ProductConvert.DTOToEntity(productDTO);
            product.setCategories(categoryDTOS);
            product.setId(id);
            return ProductConvert.EntityToDTO(productRepository.save(product));
        }
        throw new NotFoundException("NotFound.id");
    }

    @Override
    public boolean deleteProductById(Long id) {
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<ProductDTO> getProductByPage(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Product> pageResult = productRepository.findBySearchValue(searchValue, pageable);
        return pageResult.map(ProductConvert::EntityToDTO);
    }

    public Page<ProductDTO> getAllCategoryById(Long id, Pageable pageable) {
        Page<Product> products = productRepository.getAllProductByCategory(id, pageable);
        return products.map(ProductConvert::EntityToDTO);
    }

    @Override
    public List<ProductDTO> getProductByCategoryId(Long id) {
        List<Product> listProduct = productRepository.getProductByCategoriesId(id);
        List<ProductDTO> listProductDTO = new ArrayList<>();
        listProduct.forEach(p -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setDescription(p.getDescription());
            productDTO.setImage(p.getImage());
            productDTO.setPrice(p.getPrice());
            listProductDTO.add(productDTO);
        });
        return listProductDTO;
    }

    @Override
    public List<ProductDTO> getProductByCategoryName(String name) {
        List<Product> listProduct = productRepository.getProductByCategoriesName(name);
        List<ProductDTO> listProductDTO = new ArrayList<>();
        listProduct.forEach(p -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(p.getId());
            productDTO.setName(p.getName());
            productDTO.setDescription(p.getDescription());
            productDTO.setImage(p.getImage());
            productDTO.setPrice(p.getPrice());
            listProductDTO.add(productDTO);
        });
        return listProductDTO;
    }

    @Override
    public Long countProduct() {
        return productRepository.count();
    }
}
