package com.bkap.services;

import com.bkap.dto.APIResponse;
import com.bkap.dto.JwtRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 30/07/2020 - 10:33
 * @created_by Tung lam
 * @since 30/07/2020
 */
public interface RestService {
    <T> APIResponse<T> execute(
            String url, HttpMethod method, HttpHeaders headers, Object body,
            ParameterizedTypeReference<APIResponse<T>> type, Map<String, Object> values);

    <T> APIResponse<T> execute(
            String url, HttpMethod method, HttpHeaders headers, Object body,
            ParameterizedTypeReference<APIResponse<T>> type);

    APIResponse<String> getToken(String urlPrefix, HttpMethod method,
                                 JwtRequest loginRequest);

}
