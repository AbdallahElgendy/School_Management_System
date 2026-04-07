package com.global.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {
	
	@ExceptionHandler
	public ModelAndView exceptionHandler( Exception exception) {
		ModelAndView errorPage = new ModelAndView() ; 
		errorPage.setViewName("error");
		errorPage.addObject("errormsg", exception.getMessage()) ; 
		return errorPage ; 
	}

}
