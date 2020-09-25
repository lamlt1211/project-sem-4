package com.bkap.repositories;

import com.bkap.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:51
 * @created_by Tung lam
 * @since 22/07/2020
 */
@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByUsersUserName(String name);

    @Query("from Orders o where o.status = 0")
    List<Orders> findAllOrderProcessing();

    @Query("from Orders o where o.status = 1 ")
    List<Orders> findAllOrderOrderConfirm();

    @Query("from Orders o where o.status = 2 ")
    List<Orders> findAllOrderSuccess();

    @Query("from Orders o where o.status = 3 ")
    List<Orders> findAllOrderCancel();

    @Query("SELECT o FROM Orders o INNER JOIN o.users u"
            + " WHERE u.userName LIKE %:searchValue%")
    Page<Orders> findByUsers_UserName(@Param("searchValue") String searchValue, Pageable pageable);

    @Query("SELECT o FROM Orders o INNER JOIN o.users u WHERE u.userName = :userName AND o.status = :status")
    List<Orders> findByUsersUserName(@Param("userName") String userName, @Param("status") Integer status);




}
