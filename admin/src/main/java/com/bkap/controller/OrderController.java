package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.OrderDTO;
import com.bkap.dto.OrderDetailDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 14/08/2020 - 11:01
 * @created_by Tung lam
 * @since 14/08/2020
 */
@Controller
@RequestMapping("/orders")
public class OrderController {
    @Value("${api.url}")
    private String url;

    @Value("${prefix.order}")
    private String orderUrl;

    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping
    public String getListAllOrderPage(Model model,
                                      @RequestParam(defaultValue = "", required = false) String searchValue,
                                      @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                      @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                      @RequestParam(defaultValue = "id", required = false) String sortBy) {
        APIResponse<RestPageImpl<OrderDTO>> responseData = getAllOrder(searchValue, pageNo, pageSize, sortBy);
        RestPageImpl<OrderDTO> orderDTOS = null;
        if (responseData.getStatus() == 200) {
            orderDTOS = responseData.getData();
        }
        model.addAttribute("data", orderDTOS);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("searchValue", searchValue);
        return "orders";
    }

    private APIResponse<RestPageImpl<OrderDTO>> getAllOrder(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        values.put("searchValue", searchValue);
        values.put("pageNo", pageNo);
        values.put("pageSize", pageSize);
        values.put("sortBy", sortBy);
        return restService.execute(
                new StringBuilder(url).append(orderUrl).append("/page")
                        .append("?searchValue={searchValue}&page={pageNo}&size={pageSize}&sortBy={sortBy}").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<RestPageImpl<OrderDTO>>>() {
                },
                values);
    }

    @PostMapping
    public String getListAllOrdersBySearchValue(@RequestParam("table_search") String searchValue) {
        return "redirect:/orders?searchValue=" + searchValue;
    }

    // Click vào thay đỏi status trong admin
    @GetMapping("/status")
    public String changeStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setStatus(status);
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        restService.execute(
                url + orderUrl,
                HttpMethod.PUT,
                headers,
                orderDTO,
                new ParameterizedTypeReference<APIResponse<OrderDTO>>() {
                });
        return "redirect:/orders";

    }

    // details order in admin
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        APIResponse<OrderDTO> orderDTO = restService.execute(
                url + orderUrl + "/id/" + id,
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<OrderDTO>>() {
                });
        if (orderDTO != null) {
            model.addAttribute("order", orderDTO);
        }
        List<OrderDetailDTO> orderDetailDTO = restService.execute(
                url + orderUrl + "/detail?id=" + id,
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<List<OrderDetailDTO>>>() {
                }).getData();
        if (orderDetailDTO != null) {
            model.addAttribute("orderDetails", orderDetailDTO);
        }
        return "order-detail";
    }

}
