package com.bkap.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 31/07/2020 - 17:34
 * @created_by Tung lam
 * @since 31/07/2020
 */
@Configuration
public class I18NConfig implements WebMvcConfigurer {
    @Bean(name = "messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // Read file i18n/messages_XXX.properties
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setAlwaysUseMessageFormat(false);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
