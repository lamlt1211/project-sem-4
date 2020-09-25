package com.bkap.util;

import com.bkap.dto.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 19/08/2020 - 11:04
 * @created_by Tung lam
 * @since 19/08/2020
 */
@Component
public class JWTUtils {
    public String getJwtTokenFromSecurityContext() {
        return ((AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getJwtToken();
    }
}
