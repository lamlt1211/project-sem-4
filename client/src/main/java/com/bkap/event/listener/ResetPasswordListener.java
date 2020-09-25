package com.bkap.event.listener;

import com.bkap.dto.APIResponse;
import com.bkap.dto.UserDTO;
import com.bkap.event.OnResetPasswordEvent;
import com.bkap.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 07/09/2020 - 16:46
 * @created_by Tung lam
 * @since 07/09/2020
 */
@Component
public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordListener.class);

    @Value("${api.url}")
    private String url;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${prefix.user}")
    private String prefixUrl;

    @Autowired
    private RestService restService;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        this.confirmResetPassword(event);

    }

    private void confirmResetPassword(OnResetPasswordEvent event) {
        String token = null;
        APIResponse<String> response = restService.execute(
                new StringBuilder(url).append(prefixUrl).append("/generate-pass-reset-token").toString(),
                HttpMethod.POST,
                null,
                event.getUser(),
                new ParameterizedTypeReference<APIResponse<String>>() {
                },
                new HashMap<String, Object>());
        if (response.getStatus() == 200) {
            token = response.getData();
        }

        final MimeMessage email = constructEmailMessage(event, token);
        mailSender.send(email);

    }

    private final MimeMessage constructEmailMessage(final OnResetPasswordEvent event,
                                                    final String token) {
        final UserDTO user = event.getUser();
        final String recipientAddress = user.getEmail();
        final String subject = "Reset Password Confirm";
        String confirmationUrl = baseUrl + "user/changePassword?id=" + user.getId()
                + "&token=" + token;
        final String message = messages.getMessage("message.regSucc", null,
                "If you can see it, that's mean you has received mail confirmation!", event.getLocale());
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("name", user.getFullName());
            context.setVariable("signature", baseUrl);
            context.setVariable("confirm_message", message);
            context.setVariable("confirm_link", confirmationUrl);
            String html = templateEngine.process("mail/mail-template", context);

            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(env.getProperty("support.email"));
        } catch (MessagingException e) {
            LOGGER.error("Error when sent email occur: {}", e.getMessage());
        }
        return mimeMessage;
    }
}
