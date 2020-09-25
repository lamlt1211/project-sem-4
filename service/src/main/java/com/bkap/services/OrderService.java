package com.bkap.services;

import com.bkap.dto.OrderDTO;
import com.bkap.dto.OrderDetailDTO;
import com.bkap.entity.OrderDetail;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:59
 * @created_by Tung lam
 * @since 22/07/2020
 */
public interface OrderService {
    List<OrderDTO> getAllOrders();

    List<OrderDTO> getAllOrdersByUserName(String username);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO changeOrderStatus(Long id);

    OrderDTO cancelOrder(Long id);

    OrderDTO findOrderById(Long id);

    List<OrderDetailDTO> findOrderDetailById(Long id);

    List<OrderDTO> getAllOrdersProcessing();

    List<OrderDTO> getAllOrdersConfirm();

    List<OrderDTO> getAllOrdersSuccess();

    List<OrderDTO> getAllOrdersCancel();

    boolean updateOrderStatus(OrderDTO orderDTO);

    boolean cancelOrderStatus(Long orderId);

    Page<OrderDTO> getOrderByPage(String searchValue, Integer pageNo, Integer pageSize, String sortBy);

    Long countOrder();

    List<OrderDTO> getAllOrderMember(String username, int status);
}
