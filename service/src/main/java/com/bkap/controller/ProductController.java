package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.PageMetaData;
import com.bkap.dto.ProductDTO;
import com.bkap.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:24
 * @created_by Tung lam
 * @since 22/07/2020
 */
@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<APIResponse<List<ProductDTO>>> getAllProduct(Locale locale) {
        List<ProductDTO> productDTOList = productService.getAllProduct();
        APIResponse<List<ProductDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(productDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<APIResponse<List<ProductDTO>>> getAllProductByStatus(Locale locale) {
        List<ProductDTO> productDTOList = productService.getAllProductByStatus();
        APIResponse<List<ProductDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(productDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get tất cả sản phẩm
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllProductsPage(Locale locale,
                                                     @RequestParam(defaultValue = "", required = false) String searchValue,
                                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                                     @RequestParam(defaultValue = "5", required = false) Integer size,
                                                     @RequestParam(defaultValue = "id", required = false) String sortBy) {
        Page<ProductDTO> productDTOS = productService.getProductByPage(searchValue, page, size, sortBy);
        APIResponse<List<ProductDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        if (!CollectionUtils.isEmpty(productDTOS.getContent())) {
            responseData.setData(productDTOS.getContent());
            PageMetaData metaData = new PageMetaData(page, size, productDTOS.getTotalPages(), productDTOS.getNumber(), productDTOS.getTotalElements(), productDTOS.hasPrevious(), productDTOS.hasNext());
            responseData.setPageMetaData(metaData);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Get tất cả sản phẩm theo trang
    @GetMapping("/page")
    public ResponseEntity<APIResponse<Page<ProductDTO>>> getAllProducts(Locale locale,
                                                                        @RequestParam(defaultValue = "", required = false) String searchValue,
                                                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                                                        @RequestParam(defaultValue = "5", required = false) Integer size,
                                                                        @RequestParam(defaultValue = "id", required = false) String sortBy) {
        Page<ProductDTO> productDTOS = productService.getProductByPage(searchValue, page, size, sortBy);
        APIResponse<Page<ProductDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        responseData.setData(productDTOS);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDTO>> getProductById(@PathVariable("id") Long id, Locale locale) {
        ProductDTO productDTO = productService.getProductById(id);
        APIResponse<ProductDTO> response = new APIResponse<>();
        response.setData(productDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO, Locale locale) {
        ProductDTO productDTO1 = productService.createProduct(productDTO);

        APIResponse<ProductDTO> response = new APIResponse<>();
        response.setData(productDTO1);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDTO>> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id, Locale locale) {
        ProductDTO productDTO1 = productService.updateProduct(productDTO, id);
        APIResponse<ProductDTO> response = new APIResponse<>();
        response.setData(productDTO1);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Boolean>> deleteProductById(@PathVariable("id") Long id, Locale locale) {
        productService.deleteProductById(id);
        APIResponse<Boolean> response = new APIResponse<>();
        response.setData(true);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("products/{id}/{page}/{size}")
    public ResponseEntity<Object> getAllCategoryById(
            @PathVariable("id") Long id,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage = productService.getAllCategoryById(id, pageable);
        APIResponse<List<ProductDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("get product by category successful");
        if (productPage != null && !CollectionUtils.isEmpty(productPage.getContent())) {
            responseData.setData(productPage.getContent());
            PageMetaData metaData = new PageMetaData(page, size, productPage.getTotalPages(), productPage.getNumber(), productPage.getTotalElements(), productPage.hasPrevious(), productPage.hasNext());
            responseData.setPageMetaData(metaData);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // get product by category id
    @GetMapping("/shop/{id}")
    public ResponseEntity<Object> getProductByCategoryId(@PathVariable("id") Long id) {
        List<ProductDTO> productDTO = productService.getProductByCategoryId(id);
        APIResponse<List<ProductDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());

        responseData.setMessage("Find by Category successful");
        responseData.setData(productDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse<Long>> getNumberOfOrderOnHold() {
        Long productNum = productService.countProduct();
        APIResponse<Long> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(productNum);
        responseData.setMessage("get number of order on hold successfull");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
