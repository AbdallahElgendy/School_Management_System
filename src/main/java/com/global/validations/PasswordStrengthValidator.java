package com.global.validations;

import java.util.Arrays;
import java.util.List;

import com.global.annotations.PasswordValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator,String> {
	
	List<String> weakPasswords ; 

	@Override
	public void initialize(PasswordValidator constraintAnnotation) {
		weakPasswords = Arrays.asList("12345" , "password" , "qwerty") ; 
	}

	@Override
	public boolean isValid(String passwordField, ConstraintValidatorContext context) {
		return passwordField != null && (!weakPasswords.contains(passwordField));
	}

}
