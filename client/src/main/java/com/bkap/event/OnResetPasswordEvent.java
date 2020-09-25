package com.bkap.event;

import com.bkap.dto.UserDTO;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnResetPasswordEvent extends ApplicationEvent {

    private static final long serialVersionUID = -2376720440470983604L;

    private String appUrl;
    private Locale locale;
    private UserDTO user;

    public OnResetPasswordEvent(UserDTO user, Locale locale, String appUrl) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
