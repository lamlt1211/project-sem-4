package com.bkap.service.imlp;

import com.bkap.dto.APIResponse;
import com.bkap.dto.ProductDTO;
import com.bkap.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 18/08/2020 - 14:25
 * @created_by Tung lam
 * @since 18/08/2020
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestServiceImpl restService;

    @Value("${api.url}")
    private String url;

    @Value("${prefix.product}")
    private String productUrl;

    // hàm này dùng để lấy ra chi tiết sản phẩm , click sẽ nhảy vào rỏ hàng
    public ProductDTO findById(Long id) {
        return restService.execute(
                new StringBuilder(url).append(productUrl + "/").append(id).toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<ProductDTO>>() {
                },
                new HashMap<String, Object>()).getData();
    }

    public APIResponse<List<ProductDTO>> getAllPageProduct(Pageable pageable) {
        Map<String, Object> values = new HashMap<>();
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        return restService.execute(
                new StringBuilder(url).append(productUrl).append("/getAll?page=" + page + "&size=" + size).toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<List<ProductDTO>>>() {
                },
                values);
    }

    // Detail Product
    @Override
    public APIResponse<List<ProductDTO>> getProductByCategoryId(Long id, Pageable pageable) {
        return restService.exchangePaging(
                new StringBuilder(url).append(productUrl).append("/shop/" + id).toString(),
                HttpMethod.GET,
                null,
                null);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return restService.execute(
                new StringBuilder(url).append(productUrl).append("/" + id).toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<ProductDTO>>() {
                }).getData();
    }

    public List<ProductDTO> getProductByCategoryName(String name) {
        return restService.execute(
                new StringBuilder(url).append(productUrl).append("category/name/").append(name).toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<List<ProductDTO>>>() {
                }).getData();
    }

    public List<ProductDTO> getAllProduct() {
        return restService.execute(
                new StringBuilder(url).append(productUrl).toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<List<ProductDTO>>>() {
                }).getData();
    }
}
