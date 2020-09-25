package com.bkap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 12:46
 * @created_by Tung lam
 * @since 27/08/2020
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebClientApplication.class, args);
    }
}
