package com.bkap.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageSource messages;

    @Autowired
    private LocaleResolver localeResolve;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);

        Locale locale = localeResolve.resolveLocale(request);
        String errMessage = messages.getMessage("message.badCredentials", null, "Username or password not correct!",
                locale);
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errMessage = messages.getMessage("auth.message.disabled", null, "Your account is not active!", locale);
        } else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
            errMessage = messages.getMessage("auth.message.locked", null, "User account is locked!", locale);
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errMessage = messages.getMessage("auth.message.expired", null, "User account has expired!", locale);
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errMessage);
    }

}
