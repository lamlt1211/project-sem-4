package com.bkap.service.imlp;

import com.bkap.dto.APIResponse;
import com.bkap.dto.AppUserDetails;
import com.bkap.dto.RoleDTO;
import com.bkap.dto.UserDTO;
import com.bkap.entity.LoginRequest;
import com.bkap.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 15:50
 * @created_by Tung lam
 * @since 27/08/2020
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    public BCryptPasswordEncoder encoder;

    @Autowired
    private RestService restService;

    @Autowired
    private HttpServletRequest request;

    @Value("${api.url}")
    private String url;

    @Value("${prefix.user}")
    private String userUrl;

    @Value("${prefix.role}")
    private String roleUrl;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            UserDTO user;
            List<RoleDTO> roles;
            List<GrantedAuthority> grantList = new ArrayList<>();
            String password = request.getParameter("password");
            String token = this.generateToken(username, password);

            if (token != null) {
                HttpHeaders header = new HttpHeaders();
                header.setBearerAuth(token);
                Map<String, Object> values = new HashMap<>();
                values.put("username", username);
                user = restService.execute(
                        new StringBuilder(url).append(userUrl + "/username/{username}").toString(),
                        HttpMethod.GET,
                        header,
                        null,
                        new ParameterizedTypeReference<APIResponse<UserDTO>>() {
                        },
                        values).getData();

                roles = restService.execute(
                        new StringBuilder(url).append(roleUrl + "/username/{username}").toString(),
                        HttpMethod.GET,
                        header,
                        null,
                        new ParameterizedTypeReference<APIResponse<List<RoleDTO>>>() {
                        },
                        values).getData();

                if (!roles.isEmpty()) {
                    roles.forEach(o -> {
                        GrantedAuthority authority = new SimpleGrantedAuthority(o.getRoleName());
                        grantList.add(authority);
                    });
                }
                return new AppUserDetails(username, encoder.encode(password), user.getFullName(), token, grantList);
            } else {
                return new AppUserDetails(username, encoder.encode(password), null, token, grantList);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // create token để login vào client
    private String generateToken(String username, String password) {
        try {
            APIResponse<String> data = restService.getToken(
                    new StringBuilder(url).append("/authenticate/signin").toString(),
                    HttpMethod.POST,
                    new LoginRequest(username, password));
            if (data.getStatus() == 200) {
                return data.getData();
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }


}
