package com.bkap.convert;

import com.bkap.dto.OrderDTO;
import com.bkap.dto.OrderDetailDTO;
import com.bkap.entity.OrderDetail;
import com.bkap.entity.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:47
 * @created_by Tung lam
 * @since 22/07/2020
 */
public class OrderConvert {
    private OrderConvert() {
    }

    public static OrderDTO convertEntityToDTO(Orders orders) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orders.getId());
        orderDTO.setStatus(orders.getStatus());
        orderDTO.setTotalPrice(orders.getTotalPrice());
        orderDTO.setCreatedAt(orders.getCreatedAt());
        orderDTO.setUpdatedAt(orders.getUpdatedAt());
        orderDTO.setUserId(orders.getUsers().getId());
        orderDTO.setFullName(orders.getUsers().getFullName());
        orderDTO.setUserDTO(UserConvert.convertEntitytoDTO(orders.getUsers()));
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        List<OrderDetail> orderDetails = orders.getOrderDetailDTOList();
        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailDTO orderDetailDTO = OrderDetailConvert.convertEntitytoDTO(orderDetail);
            orderDetailDTOList.add(orderDetailDTO);
        }
        orderDTO.setOrderDetailDTOList(orderDetailDTOList);
        return orderDTO;
    }

    public static Orders convertDTOtoEntity(OrderDTO orders) {
        Orders orderDTO = new Orders();
        orderDTO.setId(orders.getId());
        orderDTO.setStatus(orders.getStatus());
        orderDTO.setTotalPrice(orders.getTotalPrice());
        orderDTO.setCreatedAt(orders.getCreatedAt());
        orderDTO.setUpdatedAt(orders.getUpdatedAt());
        List<OrderDetail> orderDetailDTOList = new ArrayList<>();
        List<OrderDetailDTO> orderDetails = orders.getOrderDetailDTOList();
        for (OrderDetailDTO orderDetail : orderDetails) {
            OrderDetail orderDetailDTO = OrderDetailConvert.convertDTOtoEntity(orderDetail);
            orderDetailDTOList.add(orderDetailDTO);
        }
        orderDTO.setOrderDetailDTOList(orderDetailDTOList);
        return orderDTO;
    }
}
