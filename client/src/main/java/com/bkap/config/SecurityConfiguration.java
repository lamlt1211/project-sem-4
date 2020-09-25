package com.bkap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 19/08/2020 - 10:08
 * @created_by Tung lam
 * @since 19/08/2020
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailServiceImpl")
    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    public BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(encoder);
        authenticationManagerBuilder.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/changePassword").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .antMatchers("/change-password").authenticated()
                .anyRequest().permitAll()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/authenticate")
                .defaultSuccessUrl("/home")
                /* .failureUrl("/login?error") */
                .failureHandler(authenticationFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().permitAll()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home").deleteCookies("JSESSIONID");

    }
}
