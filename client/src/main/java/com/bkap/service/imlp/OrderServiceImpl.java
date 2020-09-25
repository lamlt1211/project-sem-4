package com.bkap.service.imlp;

import com.bkap.dto.APIResponse;
import com.bkap.dto.OrderDTO;
import com.bkap.service.OrderService;
import com.bkap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 19/08/2020 - 11:09
 * @created_by Tung lam
 * @since 19/08/2020
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RestServiceImpl restService;

    @Autowired
    private JWTUtils jwtTokenUtil;

    @Value("${api.url}")
    private String url;

    @Value("${prefix.order}")
    private String preUrl;

    @Override
    public List<OrderDTO> getAllOrder(String username, int status) {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        return restService.execute(
                new StringBuilder(url).append(preUrl).append("/member?username=").append(username).append("&status=").append(status).toString(),
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<List<OrderDTO>>>() {
                }).getData();
    }

    @Override
    public OrderDTO viewOrder(Long id) {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        return restService.execute(
                new StringBuilder(url).append(preUrl).append("/view?id=").append(id).toString(),
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<OrderDTO>>() {
                }).getData();
    }

    @Override
    public Boolean cancelOrder(Long id) {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        OrderDTO orderDTO = restService.execute(
                new StringBuilder(url).append(preUrl).append("/cancel/").append(id).toString(),
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<OrderDTO>>() {
                }).getData();
        if (orderDTO != null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean createOrder(OrderDTO orderDTO) {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        if (orderDTO != null) {
            restService.execute(new StringBuilder(url).append(preUrl).toString(), HttpMethod.POST, header, orderDTO, new ParameterizedTypeReference<APIResponse<OrderDTO>>() {
            }).getData();
            return true;
        } else return false;
    }
}
