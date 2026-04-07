package com.global.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.global.model.Contact;
import com.global.service.ContactService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class ContactController {
	private final ContactService contactService ; 
	
	@Autowired
	public ContactController(ContactService contactService) {
		super();
		this.contactService = contactService;
	}
	
	@RequestMapping("/contact")
	public String displayHomePage(Model model){
		model.addAttribute("contact", new Contact()) ;
		return "contact.html" ; 
	}
	
	@RequestMapping(value = "/saveMsg" , method = RequestMethod.POST) 
	public String saveMessage(@Valid @ModelAttribute("contact") Contact contact , Errors errors) {
		if(errors.hasErrors()) {
			log.info("Validation failed due to these errors: " + errors.toString()) ; 
			return "contact.html" ; 
		}
		contactService.saveMessageDetails(contact) ; 
		return "redirect:/contact" ;
	}
	
	@GetMapping("/displayMessages/page/{pageNum}")
	public ModelAndView displayMessages(@PathVariable int pageNum , @RequestParam String sortField 
										,@RequestParam String sortDir , Model model) {
		ModelAndView modelAndView  = new ModelAndView("messages.html");
		Page<Contact> contactPages = contactService.findMsgWithOpenStatus(pageNum , sortField, sortDir) ; 
		List<Contact> contacts = contactPages.getContent() ; 
		model.addAttribute("currentPage", pageNum) ; 
		model.addAttribute("totalPages", contactPages.getTotalPages()) ; 
		model.addAttribute("totalMsgs", contactPages.getTotalElements()) ; 
		model.addAttribute("sortField", sortField) ;
		model.addAttribute("sortDir", sortDir) ; 
		model.addAttribute("reverseSortDir", sortDir.equals("asc")? "desc":"asc" ) ;
		modelAndView.addObject("contactMsgs", contacts)  ; 
		return modelAndView ; 
	} 
	
	@GetMapping("/closeMsg")
	public String closeMsg(@RequestParam int id ) {
		contactService.updateMsgStatus(id)  ;
		return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
	}
	 
	
	
	
}
