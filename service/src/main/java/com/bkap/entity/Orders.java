package com.bkap.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 30/07/2020 - 09:50
 * @created_by Tung lam
 * @since 30/07/2020
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "totalprice", precision = 20, scale = 3)
    private double totalPrice;
    @CreationTimestamp
    @Column(name = "createdat", updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updatedat")
    private Date updatedAt;
    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    private List<OrderDetail> orderDetailDTOList;
}
