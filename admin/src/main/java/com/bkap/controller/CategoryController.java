package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.CategoryDTO;
import com.bkap.services.RestService;
import com.bkap.utils.JWTUtil;
import com.bkap.utils.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 04/08/2020 - 17:18
 * @created_by Tung lam
 * @since 04/08/2020
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Value("${api.url}")
    private String url;

    @Value("${prefix.category}")
    private String prefixCategoryUrl;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RestService restService;

    // get all product by page
    @GetMapping
    public String getListAllCategoryPage(Model model,
                                         @RequestParam(defaultValue = "", required = false) String searchValue,
                                         @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                         @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                         @RequestParam(defaultValue = "id", required = false) String sortBy) {
        APIResponse<RestPageImpl<CategoryDTO>> responseData = getAllCategories(searchValue, pageNo, pageSize, sortBy);
        RestPageImpl<CategoryDTO> categories = null;
        if (responseData.getStatus() == 200) {
            categories = responseData.getData();
        }
        model.addAttribute("data", categories);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("searchValue", searchValue);
        return "category";
    }

    private APIResponse<RestPageImpl<CategoryDTO>> getAllCategories(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        values.put("searchValue", searchValue);
        values.put("pageNo", pageNo);
        values.put("pageSize", pageSize);
        values.put("sortBy", sortBy);
        return restService.execute(
                new StringBuilder(url).append(prefixCategoryUrl).append("/page")
                        .append("?searchValue={searchValue}&page={pageNo}&size={pageSize}&sortBy={sortBy}").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<RestPageImpl<CategoryDTO>>>() {
                },
                values);
    }

    // search
    @PostMapping
    public String getListAllCategoryBySearchValue(@RequestParam("table_search") String searchValue) {
        return "redirect:/categories?searchValue=" + searchValue;
    }

    // detail category
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer categoryId, Model model) {
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        CategoryDTO categoryDTO = restService.execute(
                url + prefixCategoryUrl + "/" + categoryId,
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {
                }).getData();
        if (categoryDTO != null) {
            model.addAttribute("category", categoryDTO);
        }
        return "category-detail";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("categoryDTO") CategoryDTO categoryDTO, Model model, HttpServletRequest request) {
        model.addAttribute("categoryDTO", categoryDTO);
        return "category-add";
    }

    @PostMapping("/create")
    public String create(@Valid CategoryDTO categoryDTO, BindingResult result, Errors errors) {

        if (result.hasErrors()) {
            return "category-add";
        }
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);

        APIResponse response = restService.execute(
                url + prefixCategoryUrl,
                HttpMethod.POST,
                header,
                categoryDTO,
                new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {
                });
        if (response.getData() == null) {
            result.rejectValue("name", "name", "Tên đã bị trùng");
            return "category-add";
        }
        return "redirect:/categories";

    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer categoryId, Model model) {
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        if (categoryId == null) {
            return "Error";
        }
        CategoryDTO category = restService.execute(
                url + prefixCategoryUrl + "/" + categoryId,
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {
                }).getData();
        model.addAttribute("category", category);
        return "category-edit";
    }

    @PostMapping("edit/{id}")
    public String edit(@ModelAttribute("category") CategoryDTO categoryRequest, @PathVariable("id") Integer categoryId) {
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
        restService.execute(
                url + prefixCategoryUrl + "/" + categoryId,
                HttpMethod.PUT,
                header,
                categoryRequest,
                new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {
                });
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        restService.execute(
                new StringBuilder(url).append(prefixCategoryUrl).append("/{id}").toString(),
                HttpMethod.DELETE,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<CategoryDTO>>() {
                },
                values);
        return "redirect:/categories";
    }
}
