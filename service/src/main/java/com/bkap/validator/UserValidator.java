package com.bkap.validator;


import com.bkap.entity.Users;
import com.bkap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 29/07/2020 - 13:56
 * @created_by Tung lam
 * @since 29/07/2020
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users users = (Users) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (users.getUserName().length() < 5 || users.getUserName().length() > 30) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.getUserByUserName(users.getUserName()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (users.getPassword().length() < 8 || users.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

    }
}
