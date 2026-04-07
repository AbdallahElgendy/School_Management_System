package com.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.global.model.Person;
import com.global.repository.PersonRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DashboardController {


	@Autowired
	private PersonRepository personRepository ; 
	
    @RequestMapping("/dashboard")
    public String displayDashboard(Model model,Authentication authentication , HttpSession http) {
    	Person loginPerson = personRepository.readByEmail(authentication.getName()) ;
        model.addAttribute("username", loginPerson.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if(loginPerson.getSchoolClass()!=null && loginPerson.getSchoolClass().getClassName()!=null) {
        	model.addAttribute("enrolledClass", loginPerson.getSchoolClass().getClassName()) ; 
        }
        http.setAttribute("loginPerson", loginPerson) ; 
      //  throw new RuntimeException("This is a bad day!!!!")  ; 
        return "dashboard.html";
    }

}
