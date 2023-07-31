package com.glotov.myprojectsuper.util;


import com.glotov.myprojectsuper.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerValidator implements Validator {
    public static final String LOGIN_REGEX = "^[a-zA-Z0-9_]{4,20}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    public static final String NAME_REGEX = "^[a-zA-Z]{2,15}$";
    public static final String SURNAME_REGEX = "^[a-zA-Z]{2,30}$";
    public static final String PHONE_REGEX = "^\\s*\\+?375((25|29|33|44)\\d{7})\\s*$";
    public static final String PASSWORD_REGEX = "^\\d{3}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        if (!isValidLogin(customer.getLogin())) {
            errors.rejectValue("login", "InvalidFormat");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        if (!isValidFirstName(customer.getFirstName())) {
            errors.rejectValue("firstName", "InvalidFormat");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        if (!isValidLastName(customer.getLastName())) {
            errors.rejectValue("lastName", "InvalidFormat");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty");
        if (!isValidPhone(customer.getPhone())) {
            errors.rejectValue("phone", "InvalidFormat");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!isValidEmail(customer.getEmail())) {
            errors.rejectValue("email", "InvalidFormat");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (!isValidPassword(customer.getPassword())) {
            errors.rejectValue("password", "InvalidFormat");
        }
    }

    // Other validation methods

    private boolean isValidLogin(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    private boolean isValidFirstName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    private boolean isValidLastName(String surname) {
        return surname != null && surname.matches(SURNAME_REGEX);
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches(PHONE_REGEX);
    }
}

