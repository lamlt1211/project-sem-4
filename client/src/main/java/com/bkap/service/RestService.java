package com.bkap.service;

import com.bkap.dto.APIResponse;
import com.bkap.entity.LoginRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 18/08/2020 - 14:37
 * @created_by Tung lam
 * @since 18/08/2020
 */
public interface RestService {
    <T> APIResponse<T> execute(
            String url, HttpMethod method, HttpHeaders headers, Object body,
            ParameterizedTypeReference<APIResponse<T>> type, Map<String, Object> values);

    <T> APIResponse<T> execute(
            String url, HttpMethod method, HttpHeaders headers, Object body,
            ParameterizedTypeReference<APIResponse<T>> type);

    APIResponse<String> getToken(String urlPrefix, HttpMethod method,
                                 LoginRequest loginRequest);

    <T> APIResponse<T> exchangePaging(String url, HttpMethod method, HttpHeaders headers, Object body);
}
