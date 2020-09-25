package com.bkap.services.impl;

import com.bkap.entity.ApiLog;
import com.bkap.repositories.ApiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 04/08/2020 - 10:20
 * @created_by Tung lam
 * @since 04/08/2020
 */
@Service
public class ApiLogService {
    @Autowired
    private ApiLogRepository apiLogRepository;

    public List<ApiLog> apiLogs() {
        return apiLogRepository.findAll();
    }

    public void saveApiLog(ApiLog apiLog) {
        apiLogRepository.save(apiLog);
    }
}
