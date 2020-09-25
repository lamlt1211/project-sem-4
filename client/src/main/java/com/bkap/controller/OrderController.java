package com.bkap.controller;

import com.bkap.dto.OrderDTO;
import com.bkap.dto.OrderDetailDTO;
import com.bkap.dto.ProductDTO;
import com.bkap.dto.UserDTO;
import com.bkap.entity.CartInfo;
import com.bkap.entity.CartLineInfo;
import com.bkap.service.OrderService;
import com.bkap.util.CartSupportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 16:30
 * @created_by Tung lam
 * @since 27/08/2020
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getOrderMember(ModelMap modelMap,
                                 @RequestParam("username") String username,
                                 @RequestParam(value = "status", defaultValue = "0", required = false) int status) {
        List<OrderDTO> ordersDTOs = orderService.getAllOrder(username, status);
        modelMap.addAttribute("orders", ordersDTOs);
        return "order-member";
    }

    @GetMapping("/view")
    public String viewOrder(@RequestParam("id") Long id, ModelMap model) {
        OrderDTO orderDTO = orderService.viewOrder(id);
        model.addAttribute("orders", orderDTO);
        return "order-view";
    }
    // cancel order
    @GetMapping("/cancel")
    @ResponseBody
    public boolean cancelOrderMember(@RequestParam("id") Long id) {
        return orderService.cancelOrder(id);
    }

    @ResponseBody
    @GetMapping("/create")
    public Boolean getProductByCategory(
            @RequestParam("username") String username,
            @RequestParam("fullname") String fullname,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("subtotal") double subtotal,
            @RequestParam("amount") double amount,
            @RequestParam("subquantity") double subquantity,
            HttpServletRequest request) {

        if (username != null) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setAmount(amount);
            orderDTO.setTotalPrice(subtotal);

            UserDTO userDTO = new UserDTO();
            userDTO.setFullName(fullname);
            userDTO.setUserName(username);
            orderDTO.setUserDTO(userDTO);

            CartInfo cartInfo = CartSupportUtils.getCartInSession(request);
            List<OrderDetailDTO> list = new ArrayList<>();
            List<CartLineInfo> lineInfos = cartInfo.getCartLines();
            lineInfos.forEach(l -> {
                OrderDetailDTO orderdetailDTO = new OrderDetailDTO();
                orderdetailDTO.setQuantity(l.getQuantity());
                orderdetailDTO.setPrice(l.getAmount());
                orderdetailDTO.setAmount(l.getAmount());

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(l.getProductDTO().getId());
                productDTO.setName(l.getProductDTO().getName());
                productDTO.setImage(l.getProductDTO().getImage());
                productDTO.setDescription(l.getProductDTO().getDescription());
                productDTO.setPrice(l.getProductDTO().getPrice());

                orderdetailDTO.setProductDTO(productDTO);
                list.add(orderdetailDTO);
            });
            orderDTO.setOrderDetailDTOList(list);
            boolean check = orderService.createOrder(orderDTO);
            if (check) {
                CartSupportUtils.removeCartInSession(request);
            }
            return true;
        } else {
            return false;
        }
    }

}
