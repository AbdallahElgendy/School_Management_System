package com.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.global.model.Address;
import com.global.model.Person;
import com.global.model.Profile;
import com.global.repository.PersonRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("ProfileControllerBean")
public class ProfileController {
 
	@Autowired
	private PersonRepository personRepository ; 
	
	@GetMapping("/displayProfile")
	public ModelAndView displayMessages(HttpSession http) {
		Person person = (Person) http.getAttribute("loginPerson") ; 
		Profile profile = new Profile() ; 
		profile.setName(person.getName()) ; 
		profile.setEmail(person.getEmail()) ; 
		profile.setMobileNumber(person.getMobileNumber()) ; 
		if(null!= person.getAddress()) {
			profile.setAddress1(person.getAddress().getAddress1()) ; 
			profile.setAddress2(person.getAddress().getAddress2())  ; 
			profile.setCity(person.getAddress().getCity()) ; 
			profile.setState(person.getAddress().getState()) ; 
			profile.setZipCode(person.getAddress().getZipCode()) ; 
		}
		ModelAndView modelAndView  = new ModelAndView("profile.html");
		modelAndView.addObject("profile", profile)  ; 
		return modelAndView ; 
	} 
	
	@PostMapping("/updateProfile")
	public String updateProfile(@Valid @ModelAttribute("profile") Profile profile ,Errors errors ,  HttpSession http) {
		if(errors.hasErrors()) {
			return "profile.html" ; 
		}
		Person person = (Person) http.getAttribute("loginPerson") ; 
		log.info("The id of the person is : " + person.getId()) ; 
		person.setName(profile.getName()) ; 
		person.setEmail(profile.getEmail()) ; 
		person.setMobileNumber(profile.getMobileNumber()) ; 
		if(person.getAddress()==null || !(person.getAddress().getId()>0)) {
			person.setAddress(new Address()) ; 
		}
		person.getAddress().setAddress1(profile.getAddress1()) ; 
		person.getAddress().setAddress2(profile.getAddress2()) ; 
		person.getAddress().setCity(profile.getCity()) ; 
		person.getAddress().setState(profile.getState()) ; 
		person.getAddress().setZipCode(profile.getZipCode()) ; 
		personRepository.save(person) ; 
		http.setAttribute("loginPerson", person) ; 
		return "redirect:/displayProfile" ; 
		
	}
}
