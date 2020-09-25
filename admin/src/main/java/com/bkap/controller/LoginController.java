package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.AppUserDetails;
import com.bkap.dto.PasswordDTO;
import com.bkap.dto.UserDTO;
import com.bkap.services.RestService;
import com.bkap.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 28/07/2020 - 14:53
 * @created_by Tung lam
 * @since 28/07/2020
 */
@Controller
public class LoginController {
    @Value("${api.url}")
    private String url;

    @Autowired
    private RestService restService;

    @Value("${prefix.user}")
    private String prefixUrl;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    public BCryptPasswordEncoder encoder;

    // dang nhap
    @GetMapping("login")
    public String getLoginPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // check xem co dung quyen admin hay k
        if (!auth.getPrincipal().toString().equals("anonymousUser"))
            return "redirect:/home";
        return "login";
    }

    // dang xuat
    @GetMapping("logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    // show profile
    @GetMapping("profile")
    public String profile(Model model) {
        AppUserDetails userLogin = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = null;
        Map values = new HashMap<String, Object>();
        values.put("username", userLogin.getUsername());
        APIResponse<UserDTO> responseData = restService.execute(
                new StringBuilder(url).append(prefixUrl).append("/username/{username}").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                },
                values);

        if (responseData.getStatus() == 200) {
            user = responseData.getData();
        }
        model.addAttribute("users", user);
        return "profile";
    }

    // edit profile
    @PostMapping("profile")
    public String updateProfile(@ModelAttribute("users") UserDTO userDTO) {
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        header.setContentType(MediaType.APPLICATION_JSON);
        restService.execute(
                url + prefixUrl + "/update",
                HttpMethod.POST,
                header,
                userDTO,
                new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                });
        return "redirect:/profile";
    }

    // update password
    @GetMapping("change-password")
    public String getUpdatePasswordPage(Model model) {
        model.addAttribute("password", new PasswordDTO());
        return "change-password";
    }

    @PostMapping("change-password")
    public String updatePassword(@ModelAttribute("password") @Valid PasswordDTO passwordDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("password", passwordDTO);
            return "change-password";
        } else {
            AppUserDetails userLogin = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDTO user = null;
            Map values = new HashMap<String, Object>();
            values.put("username", userLogin.getUsername());
            APIResponse<UserDTO> responseData = restService.execute(
                    new StringBuilder(url).append(prefixUrl).append("/username/{username}").toString(),
                    HttpMethod.GET,
                    null,
                    null,
                    new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                    },
                    values);

            if (responseData.getStatus() == 200) {
                user = responseData.getData();
            }
            if (encoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
                if (passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
                    user.setPassword(passwordDTO.getNewPassword());
                    try {
                        APIResponse<UserDTO> results = restService.execute(
                                new StringBuilder(url).append(prefixUrl).append("/changePassword").toString(),
                                HttpMethod.POST,
                                null,
                                user,
                                new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                                },
                                new HashMap<String, Object>());
                        if (results.getStatus() != 200) {
                            throw new RuntimeException("Something wrong!");
                        }
                        return "redirect:/home";
                    } catch (Exception e) {
                        throw e;
                    }
                } else {
                    result.rejectValue("newPassword", "something.org", "New password and confirm password not matched!");
                    model.addAttribute("password", passwordDTO);
                    return "change-password";
                }
            } else {
                result.rejectValue("currentPassword", "something.org", "Current password is not correct");
                model.addAttribute("password", passwordDTO);
                return "change-password";
            }
        }
    }


}
