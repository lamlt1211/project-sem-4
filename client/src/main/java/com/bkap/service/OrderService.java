package com.bkap.service;

import com.bkap.dto.OrderDTO;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 16:31
 * @created_by Tung lam
 * @since 27/08/2020
 */
public interface OrderService {
    List<OrderDTO> getAllOrder(String username, int status);

    OrderDTO viewOrder(Long id);

    Boolean cancelOrder(Long id);

    Boolean createOrder(OrderDTO orderDTO);
}
