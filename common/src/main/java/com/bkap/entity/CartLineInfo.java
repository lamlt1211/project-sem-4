package com.bkap.entity;

import com.bkap.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class CartLineInfo implements Serializable {
    private ProductDTO productDTO;
    private int quantity;
    private double amount;

    public CartLineInfo() {
        this.quantity = 0;
    }

//    public double getAmount() {
//        if(productDTO.getPromotions() != null){
//            productDTO.getPromotions().forEach(
//                    p -> this.amount = (productDTO.getPrice() * quantity) - ((productDTO.getPrice() * quantity) * p.getPercent() / 100)
//            );
//        }
//        else {
//            getAmountFist();
//        }
//        return amount;
//    }
    public double getAmountFist() {
        this.amount = productDTO.getPrice() * quantity;
        return this.amount;
    }
}

