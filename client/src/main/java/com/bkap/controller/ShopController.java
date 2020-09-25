package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.CategoryDTO;
import com.bkap.dto.PageMetaData;
import com.bkap.dto.ProductDTO;
import com.bkap.entity.CartInfo;
import com.bkap.service.CategoryService;
import com.bkap.service.ProductService;
import com.bkap.util.CartSupportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 16:26
 * @created_by Tung lam
 * @since 27/08/2020
 */
@Controller
public class ShopController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    // show all product bên client - done
    @GetMapping("/shop")
    public String page(@RequestParam(name = "id", required = true, defaultValue = "0") Long id,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "8") Integer size,
                       ModelMap modelMap, HttpServletRequest request) {
        if (id == 0){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(0L);
            modelMap.addAttribute("categoryDTO", categoryDTO);

            APIResponse<List<ProductDTO>> responseDTO = productService.getAllPageProduct(PageRequest.of(page, size));
            PageMetaData metaData = responseDTO.getPageMetaData();
            modelMap.addAttribute("listProduct", responseDTO.getData());
            if (metaData != null) {
                modelMap.addAttribute("pages", metaData);
            }
        }
        else {
            CategoryDTO categoryDTO = categoryService.getCategoryById(id);
            modelMap.addAttribute("categoryDTO", categoryDTO);

            APIResponse<List<ProductDTO>> responseDTO = productService.getProductByCategoryId(id, PageRequest.of(page, size));
            PageMetaData metaData = responseDTO.getPageMetaData();
            modelMap.addAttribute("listProduct", responseDTO.getData());
            if (metaData != null) {
                modelMap.addAttribute("pages", metaData);
            } else {
                metaData = new PageMetaData();
                metaData.setFirst(false);
                metaData.setLast(false);
                metaData.setNumber(0);
                metaData.setPage(0);
                metaData.setSize(0);
                metaData.setTotalElements(0);
                metaData.setTotalPages(0);
                modelMap.addAttribute("pages", metaData);
            }
        }
        List<CategoryDTO> listCategory = categoryService.getAllCategory();
        modelMap.addAttribute("listCategory", listCategory);

        CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
        modelMap.addAttribute("sizeCart", cartInfo.getCartLines().size());
        return "shop";
    }

    // Lọc sản phẩm theo category - chưa done
    @GetMapping("/shop/{name}")
    public String getProductByCategory(@PathVariable(name = "name") String name, Model model) {
        List<ProductDTO> listProduct = productService.getProductByCategoryName(name);
        List<CategoryDTO> listCategory = categoryService.getAllCategory();

        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listProduct", listProduct);
        return "shop";
    }

    // detail product - done
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(name = "id") Long id,
                         Model model, HttpServletRequest request) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
        model.addAttribute("sizeCart", cartInfo.getCartLines().size());
        return "product-detail";
    }

    @GetMapping("/{id}")
    public String category(@PathVariable(name = "id") Long id,
                           Model model) {
        CategoryDTO category = categoryService.getCategoryById(id);

        model.addAttribute("category", category);
        return "shop";
    }
}
