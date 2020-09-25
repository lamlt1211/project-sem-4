package com.bkap.services;

import com.bkap.dto.APIResponse;
import com.bkap.dto.AppUserDetails;
import com.bkap.dto.JwtRequest;
import com.bkap.dto.RoleDTO;
import com.bkap.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 30/07/2020 - 17:47
 * @created_by Tung lam
 * @since 30/07/2020
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public BCryptPasswordEncoder encoder;

    @Autowired
    private RestService restService;

    @Value("${api.url}")
    private String url;

    @Value("${prefix.authenticate}")
    private String prefixAuthUrl;

    @Value("${prefix.user}")
    private String prefixUserUrl;

    @Value("${prefix.role}")
    private String prefixRoleUrl;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            String password = request.getParameter("password");
            String apiToken = new StringBuilder(url).append(prefixAuthUrl).append("/signin").toString();
            String token = restService.getToken(apiToken,
                    HttpMethod.POST,
                    new JwtRequest(username, password)).getData();

            Map<String, Object> values = new HashMap<>();
            values.put("username", username);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            UserDTO userDTO = restService.execute(
                    new StringBuilder(url).append(prefixUserUrl).append("/username/").append(username).toString(),
                    HttpMethod.GET,
                    headers,
                    null,
                    new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                    },
                    values).getData();

            List<RoleDTO> roleDTOS = restService.execute(
                    new StringBuilder(url).append(prefixRoleUrl).append("/username/").append(username).toString(),
                    HttpMethod.GET,
                    headers,
                    null,
                    new ParameterizedTypeReference<APIResponse<List<RoleDTO>>>() {
                    },
                    values).getData();

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (!roleDTOS.isEmpty()) {
                roleDTOS.forEach(o -> {
                    GrantedAuthority authority = new SimpleGrantedAuthority(o.getRoleName());
                    grantedAuthorities.add(authority);
                });
            }
            return new AppUserDetails(username, encoder.encode(password), userDTO.getFullName(), token, grantedAuthorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found!", e);
        }
    }
}
