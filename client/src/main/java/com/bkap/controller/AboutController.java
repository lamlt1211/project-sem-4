package com.bkap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 15/09/2020 - 15:58
 * @created_by Tung lam
 * @since 15/09/2020
 */
@Controller
@RequestMapping("/about")
public class AboutController {
    @GetMapping
    public String redirectToHomePage() {
        return "about";
    }
}
