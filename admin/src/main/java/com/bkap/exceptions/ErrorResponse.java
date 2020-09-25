package com.bkap.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 10/09/2020 - 11:29
 * @created_by Tung lam
 * @since 10/09/2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private String message;
}
