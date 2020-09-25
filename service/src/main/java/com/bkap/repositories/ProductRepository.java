package com.bkap.repositories;

import com.bkap.entity.Product;
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
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("from Product p where p.status =1")
    List<Product> findAllProductByStatus();

    Product findByName(String name);

    // query ham` search , search theo name va description
    @Query("SELECT p FROM Product p"
            + " WHERE (p.name LIKE %:searchValue% OR p.description LIKE %:searchValue%)")
    // phan trang
    Page<Product> findBySearchValue(@Param("searchValue") String searchValue, Pageable pageable);

    @Query(value = "SELECT p FROM Product p INNER JOIN p.categories c WHERE c.id = :id")
    Page<Product> getAllProductByCategory(@Param("id") Long id, Pageable pageable);

    List<Product> getProductByCategoriesId(Long id);

    List<Product> getProductByCategoriesName(String name);

}
