package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.UserDTO;
import com.bkap.services.RestService;
import com.bkap.utils.JWTUtil;
import com.bkap.utils.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 13/08/2020 - 14:07
 * @created_by Tung lam
 * @since 13/08/2020
 */
@Controller
@RequestMapping("/users")
public class UserController {
    @Value("${api.url}")
    private String url;

    @Value("${prefix.user}")
    private String usersUrl;

    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping
    public String getListAllUserPage(Model model,
                                     @RequestParam(defaultValue = "", required = false) String searchValue,
                                     @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                     @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                     @RequestParam(defaultValue = "id", required = false) String sortBy) {
        APIResponse<RestPageImpl<UserDTO>> responseData = getAllUser(searchValue, pageNo, pageSize, sortBy);
        RestPageImpl<UserDTO> userDTOS = null;
        if (responseData.getStatus() == 200) {
            userDTOS = responseData.getData();
        }
        model.addAttribute("data", userDTOS);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("searchValue", searchValue);
        return "users";
    }

    private APIResponse<RestPageImpl<UserDTO>> getAllUser(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Map<String, Object> values = new HashMap<>();
        values.put("searchValue", searchValue);
        values.put("pageNo", pageNo);
        values.put("pageSize", pageSize);
        values.put("sortBy", sortBy);
        return restService.execute(
                new StringBuilder(url).append(usersUrl).append("/page")
                        .append("?searchValue={searchValue}&page={pageNo}&size={pageSize}&sortBy={sortBy}").toString(),
                HttpMethod.GET,
                null,
                null,
                new ParameterizedTypeReference<APIResponse<RestPageImpl<UserDTO>>>() {
                },
                values);
    }

    @PostMapping
    public String getListAllUserBySearchValue(
            @RequestParam("table_search") String searchValue) {
        return "redirect:/users?searchValue=" + searchValue;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) { // dùng để hiển thị chi tiết user
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        UserDTO userDTO = restService.execute(
                url + usersUrl + "/" + id,
                HttpMethod.GET,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                }).getData();
        if (userDTO != null) {
            model.addAttribute("users", userDTO);
        }
        return "users-detail";
    }

    @PostMapping("/user/toggle-block") // thay doi status user in admin
    public String changeUserStatus(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam Integer id,
            @RequestParam Integer status) {
        String authToken = jwtUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("status", status);
        restService.execute(
                url + usersUrl + "/toggle-block?id={id}&status={status}",
                HttpMethod.POST,
                header,
                null,
                new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                },
                values);
        return "redirect:/users?searchValue=" + searchValue;
    }


}
