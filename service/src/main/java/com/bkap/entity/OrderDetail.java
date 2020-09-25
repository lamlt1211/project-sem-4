package com.bkap.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 30/07/2020 - 10:04
 * @created_by Tung lam
 * @since 30/07/2020
 */
@Entity
@Table(name = "orderdetail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price", precision = 20, scale = 3)
    private double price;
    @CreationTimestamp
    @Column(name = "createdat", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat")
    private Date updatedAt;
    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;
}
