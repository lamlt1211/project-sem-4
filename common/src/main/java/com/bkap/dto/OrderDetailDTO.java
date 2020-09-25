package com.bkap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 30/07/2020 - 10:02
 * @created_by Tung lam
 * @since 30/07/2020
 */
@Getter
@Setter
@AllArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private int quantity;
    private double price;
    private Date createdAt;
    private Date updateAt;
    private int status;

    private ProductDTO productDTO;
    private double amount;

    public OrderDetailDTO() {
        this.quantity = 0;
    }

    public double getAmount() {
        return this.productDTO.getPrice() * this.quantity;
    }
}
