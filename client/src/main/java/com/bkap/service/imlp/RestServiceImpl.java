package com.bkap.service.imlp;

import com.bkap.dto.APIResponse;
import com.bkap.entity.LoginRequest;
import com.bkap.exception.RestTemplateException;
import com.bkap.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 18/08/2020 - 14:35
 * @created_by Tung lam
 * @since 18/08/2020
 */
@Service
public class RestServiceImpl implements RestService {
    @Autowired
    private RestTemplate restTemplate;

    public <T> APIResponse<T> execute(String url, HttpMethod method, HttpHeaders headers, Object body, ParameterizedTypeReference<APIResponse<T>> type, Map<String, Object> values) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<APIResponse<T>> responseEntity = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type,
                    values);
            if (responseEntity.getStatusCodeValue() >= HttpStatus.OK.value()
                    && responseEntity.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return responseEntity.getBody();
            }
            throw new RestTemplateException(responseEntity.getBody().getMessage());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    public <T> APIResponse<T> execute(String url, HttpMethod method, HttpHeaders headers, Object body, ParameterizedTypeReference<APIResponse<T>> type) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<APIResponse<T>> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type);
            if (response.getStatusCodeValue() >= HttpStatus.OK.value()
                    && response.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return response.getBody();
            }
            throw new RestTemplateException(response.getBody().getMessage());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public APIResponse<String> getToken(String urlPrefix, HttpMethod method, LoginRequest loginRequest) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(loginRequest);
            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    urlPrefix,
                    method,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            if (response.getBody().getStatus() >= HttpStatus.OK.value() && response.getBody().getStatus() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return response.getBody();
            }
            throw new RestTemplateException(response.getBody().getMessage());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public <T> APIResponse<T> exchangePaging(String url, HttpMethod method, HttpHeaders headers, Object body) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ParameterizedTypeReference<APIResponse<T>> reponseType = new ParameterizedTypeReference<APIResponse<T>>() {
            };
            ResponseEntity<APIResponse<T>> res = restTemplate.exchange(url, method, entity, reponseType);
            if (res.getStatusCodeValue() >= HttpStatus.OK.value() && res.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return res.getBody();
            }
            throw new RestTemplateException(res.getBody().getMessage());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }
}
