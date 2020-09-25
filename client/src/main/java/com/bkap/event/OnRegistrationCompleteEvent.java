package com.bkap.event;

import com.bkap.dto.UserDTO;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 27/08/2020 - 18:13
 * @created_by Tung lam
 * @since 27/08/2020
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private static final long serialVersionUID = 7080691273130328023L;

    private String appUrl;
    private Locale locale;
    private UserDTO user;

    public OnRegistrationCompleteEvent(UserDTO user, Locale locale, String appUrl) {
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
