package com.bkap.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 18/08/2020 - 14:41
 * @created_by Tung lam
 * @since 18/08/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
