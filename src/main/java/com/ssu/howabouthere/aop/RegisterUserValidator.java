package com.ssu.howabouthere.aop;

import com.ssu.howabouthere.vo.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegisterUserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User registerUser = (User) target;

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(registerUser.getEmail());

        if(!matcher.matches()) {
            errors.rejectValue("email", "이메일 형식이 올바르지 않습니다.");
        }
    }
}
