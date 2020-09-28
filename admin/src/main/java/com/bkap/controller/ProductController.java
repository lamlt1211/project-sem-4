package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.CategoryDTO;
import com.bkap.dto.ProductDTO;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 06/08/2020 - 11:14
 * @created_by Tung lam
 * @since 06/08/2020
 */
@Controller
@RequestMapping("/products")
public class ProductController {
    @Value("${api.url}")
    private String url;

    @Value("${prefix.product}")
    private String productUrl;

    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtil jwtUtil;

    // get all product phân trang
    @GetMapping
    public String getListAllProductPage(Model model,
                                        @RequestParam(defaultValue = "", required = false) String searchValue,
                                        @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                        @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                        @RequestParam(defaultValue = "id", required = false) String sortBy) {
        APIResponse<RestPageImpl<ProductDTO>> responseData = getAllProduct(searchValue, pageNo, pageSize, sortBy);
        RestPageImpl<ProductDTO> productDTOS = null;
        if (responseData.getStatus() == 200) {
            productDTOS = responseData.getData();
        }
        model.addAttribute("data", productDTOS);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("searchValue", searchValue);
        return "products";
    }

    private APIResponse<RestPageImpl<ProductDTO>> getAllProduct(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        values.put("searchValue", searchValue);
        values.put("pageNo", pageNo);
        values.put("pageSize", pageSize);
        values.put("sortBy", sortBy);
        return restService.execute(
                new StringBuilder(url).append(productUrl).append("/page")
                        .append("?searchValue={searchValue}&page={pageNo}&size={pageSize}&sortBy={sortBy}").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<RestPageImpl<ProductDTO>>>() {
                },
                values);
    }

    @PostMapping
    public String getListAllProductBySearchValue(@RequestParam("table_search") String searchValue) {
        return "redirect:/products?searchValue=" + searchValue;
    }

    @GetMapping("/create")
    public String show(@ModelAttribute("productDTO") ProductDTO productDTO, Model model) {  // hàm này show lên dữ liệu để create
        List<CategoryDTO> categoryDTOList = restService.execute(new StringBuilder(url).append("/categories/status").toString(), HttpMethod.GET, null, null,
                new ParameterizedTypeReference<APIResponse<List<CategoryDTO>>>() {
                }).getData();
        model.addAttribute("categoryDTOList", categoryDTOList);
        model.addAttribute("productRequest", productDTO);
        return "products-add";
    }

    @PostMapping("/create") // xử lý create
    public String create(@Valid ProductDTO productDTO, BindingResult result, Errors errors, Model model) {
        if (result.hasErrors()) {
            List<CategoryDTO> categoryDTOList = restService.execute(new StringBuilder(url).append("/categories").toString(), HttpMethod.GET, null, null,
                    new ParameterizedTypeReference<APIResponse<List<CategoryDTO>>>() {
                    }).getData();
            model.addAttribute("categoryDTOList", categoryDTOList);
            System.out.println(result.getAllErrors());
            return "products-add";
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        APIResponse response = restService.execute(
                url + productUrl,
                HttpMethod.POST,
                header,
                productDTO,
                new ParameterizedTypeReference<APIResponse<ProductDTO>>() {
                });
        if (response.getData() == null) {
            result.rejectValue("name", "name", "Tên sản phẩm đã bị trùng");
            return "products-add";
        }
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) { // show product details
        HttpHeaders header = new HttpHeaders();
        ProductDTO productDTO = restService.execute(
                url + productUrl + "/" + id,
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<ProductDTO>>() {
                }).getData();
        if (productDTO != null) {
            model.addAttribute("product", productDTO);
        }
        return "products-detail";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) { // khi click vào nút sửa , hàm này thực hiện show lên dữ liệu đã có
        HttpHeaders header = new HttpHeaders();
        if (id == null) {
            return "Error";
        }
        ProductDTO productDTO = restService.execute(
                url + productUrl + "/" + id,
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<ProductDTO>>() {
                }).getData();
        List<Long> selectedCategories = productDTO.getCategoryDTOList().stream().map(CategoryDTO::getId)
                .collect(Collectors.toList()); // 2 : hàm này dùng để chọn đúng cái categoris khi show ra , nếu ko nó sẽ trả ra all
        List<CategoryDTO> categoryDTOList = restService.execute(new StringBuilder(url).append("/categories").toString(), HttpMethod.GET, null, null,
                new ParameterizedTypeReference<APIResponse<List<CategoryDTO>>>() {
                }).getData(); // 3 :  hàm này dufmg để show ra category

        model.addAttribute("product", productDTO); // 3 :
        model.addAttribute("categoryDTOList", categoryDTOList);
        model.addAttribute("selectedCategories", selectedCategories); // 2 : đây là hàm attribute của hàm trên
        return "products-edit";
    }

    @PostMapping("edit/{id}")
    public String edit(@ModelAttribute("product") ProductDTO productDTO, @PathVariable("id") Integer id) {// hàm này dùng để xử lý , khi edit
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        restService.execute(
                url + productUrl + "/" + id,
                HttpMethod.PUT,
                header,
                productDTO,
                new ParameterizedTypeReference<APIResponse<ProductDTO>>() {
                });
        return "redirect:/products";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            restService.execute(
                    url + productUrl + "/" + id,
                    HttpMethod.DELETE,
                    headers,
                    null,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
        } catch (Exception e) {
            return "redirect:/products?error=true";
        }
        return "redirect:/products";
    }

}
