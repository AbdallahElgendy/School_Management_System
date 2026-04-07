package com.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.global.model.Person;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
	
	@GetMapping("/displayCourses")
	public ModelAndView displayCourses(HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView("courses_enrolled.html") ; 
		Person loginPerson = (Person) httpSession.getAttribute("loginPerson") ; 
		modelAndView.addObject("person", loginPerson) ; 
		return modelAndView ; 
	}
}
