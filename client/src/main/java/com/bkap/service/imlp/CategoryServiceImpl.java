package com.bkap.service.imlp;

import com.bkap.dto.APIResponse;
import com.bkap.dto.CategoryDTO;
import com.bkap.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 18/08/2020 - 14:26
 * @created_by Tung lam
 * @since 18/08/2020
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private RestServiceImpl restTemplateService;

    @Value("${api.url}")
    private String url;

    // get tat ca Category
    @Override
    public List<CategoryDTO> getAllCategory() {
        return restTemplateService.execute(
                new StringBuilder(url).append("categories").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<List<CategoryDTO>>>() {
                }).getData();
    }

    // get Category theo id
    @Override
    public CategoryDTO getCategoryById(Long id) {
        return restTemplateService.execute(
                new StringBuilder(url).append("categories/").append(id).toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {
                }).getData();
    }
}
