package com.bkap.repositories;

import com.bkap.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 14/08/2020 - 15:46
 * @created_by Tung lam
 * @since 14/08/2020
 */
@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Long> {
}
