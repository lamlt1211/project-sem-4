package com.bkap.services;

import com.bkap.dto.APIResponse;
import com.bkap.dto.JwtRequest;
import com.bkap.exceptions.RestTemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @created_at 30/07/2020 - 17:25
 * @created_by Tung lam
 * @since 30/07/2020
 */
@Service
public class RestServiceImpl implements RestService {
    private static final Logger logger = LoggerFactory.getLogger(RestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> APIResponse<T> execute(String url, HttpMethod method, HttpHeaders headers,
                                      Object body, ParameterizedTypeReference<APIResponse<T>> type,
                                      Map<String, Object> values) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<APIResponse<T>> responseEntity = restTemplate.exchange(
                    url, method, entity, type, values);
            if (responseEntity.getStatusCodeValue() >= HttpStatus.OK.value()
                    && responseEntity.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return responseEntity.getBody();
            }
            logger.info("Can't get data from API - {}", responseEntity.getBody().getMessage());
            throw new RestTemplateException(responseEntity.getBody().getMessage());

        } catch (Exception e) {
            logger.info("Some error has occur when call API - {}", e.getMessage());
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public <T> APIResponse<T> execute(String url, HttpMethod method, HttpHeaders headers,
                                      Object body, ParameterizedTypeReference<APIResponse<T>> type) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<APIResponse<T>> responseEntity = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type);
            return responseEntity.getBody();
        } catch (Exception e) {
            logger.info("Some error has occur when call API - {}", e.getMessage());
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public APIResponse<String> getToken(String urlPrefix, HttpMethod method, JwtRequest loginRequest) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(loginRequest);
            ResponseEntity<APIResponse<String>> responseEntity = restTemplate.exchange(
                    urlPrefix,
                    method,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            if (responseEntity.getStatusCodeValue() >= HttpStatus.OK.value() && responseEntity.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return responseEntity.getBody();
            }
            logger.info("Can't get data from API - {}", responseEntity.getBody().getMessage());
            throw new RestTemplateException(responseEntity.getBody().getMessage());
        } catch (Exception e) {
            logger.info("Some error has occur when call API - {}", e.getMessage());
            throw new RestTemplateException(e.getMessage(), e);
        }
    }
}
