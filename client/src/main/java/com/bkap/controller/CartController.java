package com.bkap.controller;

import com.bkap.dto.ProductDTO;
import com.bkap.entity.CartInfo;
import com.bkap.entity.CartLineInfo;
import com.bkap.service.ProductService;
import com.bkap.util.CartSupportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 12:45
 * @created_by Tung lam
 * @since 27/08/2020
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewCart(HttpServletRequest request, Model model) {
        CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
        model.addAttribute("myCart", cartInfo);
        model.addAttribute("myCartProduct", cartInfo.getCartLines());
        model.addAttribute("sizeCart", cartInfo.getCartLines().size());
        return "cart";
    }

    @GetMapping("/buycart")
    @ResponseBody
    public Integer buyProduct(HttpServletRequest request, @RequestParam(value = "id", defaultValue = "") Long id,
                              @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        ProductDTO productDTO = null;
        if (id != null && id > 0) {
            productDTO = productService.findById(id);
        }
        if (productDTO != null) {
            CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
            ProductDTO productDTO1 = new ProductDTO(productDTO);
            cartInfo.addProduct(productDTO1, quantity);
            return cartInfo.getCartLines().size();
        } else {
            return 0;
        }

    }

    @GetMapping("/removecart")
    @ResponseBody
    public Integer removeProduct(HttpServletRequest request,
                                 @RequestParam(value = "id", defaultValue = "") Long id) {
        ProductDTO productDTO = null;
        if (id != null && id > 0) {
            productDTO = productService.findById(id);
        }
        if (productDTO != null) {

            CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
            ProductDTO product = new ProductDTO(productDTO);
            cartInfo.removeProduct(product);
            return cartInfo.getCartLines().size();
        } else {
            return 0;
        }
    }

    @GetMapping("/updatecart")
    @ResponseBody
    public Double updateProduct(HttpServletRequest request,
                                @RequestParam(value = "id", defaultValue = "") Long id,
                                @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        ProductDTO productDTO = null;
        if (id != null && id > 0) {
            productDTO = productService.findById(id);
        }
        if (productDTO != null) {
            CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
            ProductDTO product = new ProductDTO(productDTO);
            cartInfo.addProduct2(product, quantity);
            CartLineInfo line = cartInfo.findLineByCode(productDTO.getId());
            return line.getAmount();
        }
        return 0.0;
    }


}
