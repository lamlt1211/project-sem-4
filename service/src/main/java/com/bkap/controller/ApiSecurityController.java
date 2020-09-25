package com.bkap.controller;

import com.bkap.dto.APIResponse;
import com.bkap.dto.JwtRequest;
import com.bkap.securities.JWTUtils;
import com.bkap.securities.JwtUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 22/07/2020 - 17:23
 * @created_by Tung lam
 * @since 22/07/2020
 */
@RestController
@RequestMapping(value = "/api/authenticate")
public class ApiSecurityController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtTokenUtil;

    @Autowired
    private JwtUserDetailServiceImpl userDetailsService;

    // create ra token
    @PostMapping("/signin")
    public ResponseEntity<APIResponse<Object>> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest, Locale locale) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if (!userDetails.isAccountNonLocked()) {
            return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), messageSource.getMessage("Authenticate.locked.account", null, locale)), HttpStatus.BAD_REQUEST);
        }
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(userDetails);
        APIResponse<Object> objectAPIResponse = new APIResponse<>();
        objectAPIResponse.setData(token);
        objectAPIResponse.setMessage(messageSource.getMessage("Message.statusAPI.ok", null, locale));
        objectAPIResponse.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(objectAPIResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

}
