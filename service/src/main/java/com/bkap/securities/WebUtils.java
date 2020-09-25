package com.bkap.securities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:56
 * @created_by Tung lam
 * @since 22/07/2020
 */
public class WebUtils {
    private WebUtils() {

    }

    public static String toString(User users) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserName:").append(users.getUsername());

        Collection<GrantedAuthority> authorities = users.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            stringBuilder.append(" (");
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    stringBuilder.append(a.getAuthority());
                    first = false;
                } else {
                    stringBuilder.append(", ").append(a.getAuthority());
                }
            }
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }
}
