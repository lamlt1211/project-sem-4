package com.bkap.convert;

import com.bkap.dto.OrderDetailDTO;
import com.bkap.dto.ProductDTO;
import com.bkap.entity.OrderDetail;
import com.bkap.entity.Product;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:47
 * @created_by Tung lam
 * @since 22/07/2020
 */
public class OrderDetailConvert {
    private OrderDetailConvert() {
    }

    public static OrderDetailDTO convertEntitytoDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setPrice(orderDetail.getPrice());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setCreatedAt(orderDetail.getCreatedAt());
        orderDetailDTO.setUpdateAt(orderDetail.getUpdatedAt());
        ProductDTO productDTO = ProductConvert.EntityToDTO(orderDetail.getProduct());
        orderDetailDTO.setProductDTO(productDTO);
        return orderDetailDTO;
    }

    public static OrderDetail convertDTOtoEntity(OrderDetailDTO orderDetail) {
        OrderDetail orderDetailDTO = new OrderDetail();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setPrice(orderDetail.getPrice());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setCreatedAt(orderDetail.getCreatedAt());
        orderDetailDTO.setUpdatedAt(orderDetail.getUpdateAt());
        Product productDTO = ProductConvert.DTOToEntity(orderDetail.getProductDTO());
        orderDetailDTO.setProduct(productDTO);
        return orderDetailDTO;
    }
}
