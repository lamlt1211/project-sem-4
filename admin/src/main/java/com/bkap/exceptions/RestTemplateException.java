package com.bkap.exceptions;


/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 31/07/2020 - 09:49
 * @created_by Tung lam
 * @since 31/07/2020
 */
public class RestTemplateException extends RuntimeException {
    private static final long serialVersionUID = -3242126885270683621L;

    public RestTemplateException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RestTemplateException(String message) {
        super(message);
    }

    public RestTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
