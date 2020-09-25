package com.bkap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 23/07/2020 - 11:36
 * @created_by Tung lam
 * @since 23/07/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private double totalPrice;
    private Date createdAt;
    private Date updatedAt;
    private int status;
    private Long userId;
    private String fullName;
    private UserDTO userDTO;
    private double amount;
    private List<OrderDetailDTO> orderDetailDTOList;
}
