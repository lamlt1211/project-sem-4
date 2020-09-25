package com.bkap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 16:03
 * @created_by Tung lam
 * @since 27/08/2020
 */
@Controller
public class LoginController {
    @Qualifier("userDetailServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("login")
    public String getLoginPage(Model model, HttpServletRequest request) {
        return "login";
    }

    @PostMapping("/user/authenticate")
    public String authenticateUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {

        UserDetails userDetail = userDetailsService.loadUserByUsername(
                username);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/forgotPass";
    }

    @GetMapping("logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index";
    }
}
