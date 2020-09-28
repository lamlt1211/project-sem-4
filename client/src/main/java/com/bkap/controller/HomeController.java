package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.ProductDTO;
import com.bkap.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 16:15
 * @created_by Tung lam
 * @since 27/08/2020
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String redirectToHomePage() {
        return "redirect:/home";
    }

    // All product
    @GetMapping("home")
    public String getIndex(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "8") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        APIResponse<List<ProductDTO>> productDTOList = productService.getAllPageProduct(pageable);
        model.addAttribute("listProducts", productDTOList.getData());
        model.addAttribute("pages", productDTOList.getPageMetaData());
        return "index";
    }
}
