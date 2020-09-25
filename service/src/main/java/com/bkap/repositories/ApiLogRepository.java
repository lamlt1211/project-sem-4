package com.bkap.repositories;

import com.bkap.entity.ApiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 04/08/2020 - 10:22
 * @created_by Tung lam
 * @since 04/08/2020
 */
@Repository
public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {
}
