package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.CategoryDTO;
import com.bkap.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:23
 * @created_by Tung lam
 * @since 22/07/2020
 */
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageSource messageSource;

    // get all category
    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryDTO>>> getAllCategory(Locale locale) {
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        APIResponse<List<CategoryDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(categoryDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get all category status active -(trang thai = 1)
    @GetMapping("/status")
    public ResponseEntity<APIResponse<List<CategoryDTO>>> getAllCategoryStatusActive(Locale locale) {
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoryStatusActive();
        APIResponse<List<CategoryDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(categoryDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get all category status unactive (trang thai = 0 )
    @GetMapping("/unactive")
    public ResponseEntity<APIResponse<List<CategoryDTO>>> getAllCategoryStatusUnActive(Locale locale) {
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoryStatusUnActive();
        APIResponse<List<CategoryDTO>> response = new APIResponse<>();
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        response.setStatus(HttpStatus.OK.value());
        response.setData(categoryDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<APIResponse<Page<CategoryDTO>>> getAllCategories(Locale locale,
                                                                           @RequestParam(defaultValue = "", required = false) String searchValue,
                                                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                                                           @RequestParam(defaultValue = "5", required = false) Integer size,
                                                                           @RequestParam(defaultValue = "id", required = false) String sortBy) {
        Page<CategoryDTO> categories = categoryService.getCategoryByPage(searchValue, page, size, sortBy);
        APIResponse<Page<CategoryDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        responseData.setData(categories);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // get Category by Id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> getCategoryById(@PathVariable("id") Long id, Locale locale) {
        CategoryDTO categoryDTO = categoryService.getCateById(id);
        APIResponse<CategoryDTO> response = new APIResponse<>();
        response.setData(categoryDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, Locale locale) {
        CategoryDTO categoryDTO1 = categoryService.createCategory(categoryDTO);
        APIResponse<CategoryDTO> response = new APIResponse<>();
        response.setData(categoryDTO1);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id, Locale locale) {
        CategoryDTO categoryDTO1 = categoryService.updateCategory(categoryDTO, id);
        APIResponse<CategoryDTO> response = new APIResponse<>();
        response.setData(categoryDTO1);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> deleteCategory(@PathVariable("id") Long id, Locale locale) {
        CategoryDTO categoryDTO1 = categoryService.deleteCategory(id);
        APIResponse<CategoryDTO> response = new APIResponse<>();
        response.setData(categoryDTO1);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse<Long>> countCategory() {
        Long categoryNum = categoryService.countCategory();
        APIResponse<Long> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData(categoryNum);
        responseData.setMessage("get number of user unblocked successfull");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
