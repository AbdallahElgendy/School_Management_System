package com.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.global.model.Person;
import com.global.service.PersonService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/public")
public class PublicController {
	@Autowired
	private PersonService personService  ; 
	
	@PostMapping(value = "/createUser") 
	public String createUser(@Valid @ModelAttribute("person") Person person , Errors errors) {
		boolean isSaved = false  ; 
		if(errors.hasErrors()) {
			return "register.html" ; 
		}
		isSaved  = personService.createNewPerson(person) ; 
		if(isSaved) {
			return "redirect:/login?register=true" ;
		}
		else {
			return "register.html" ; 
		}
		
	}
	
	@GetMapping("/register")
	public String displayRegisterPage(Model model){
		model.addAttribute("person", new Person()) ;
		return "register.html" ; 
	}

}
