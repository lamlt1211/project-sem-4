package com.bkap.utils;

import com.bkap.dto.AppUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 04/08/2020 - 15:27
 * @created_by Tung lam
 * @since 04/08/2020
 */
@Component
public class JWTUtil {
    public static String getJwtTokenFromSecurityContext() {
        return ((AppUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getJwtToken();
    }

    public static HttpHeaders getHeader() {
        String authToken = JWTUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
        return header;
    }
}
