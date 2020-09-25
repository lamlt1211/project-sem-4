package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.AppUserDetails;
import com.bkap.dto.UserDTO;
import com.bkap.service.RestService;
import com.bkap.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 16:27
 * @created_by Tung lam
 * @since 27/08/2020
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtils jwtTokenUtil;

    @Value("${prefix.user}")
    private String prefixUrl;

    @Value("${api.url}")
    private String url;

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

    @PostMapping("profile")
    public String updateProfile(@ModelAttribute("users") UserDTO userDTO) {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
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

}
