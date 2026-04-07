package com.global.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.global.validations.FieldsValueMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {
	Class<?> [] groups() default {} ; 
	Class<? extends Payload> [] payload() default {} ; 
	
	String message() default "Fields Values do not match!!" ;
	String field() ; 
	String fieldMatch() ; 
	
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface list{
		FieldsValueMatch[] value(); 
	}

}
