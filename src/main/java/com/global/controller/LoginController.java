package com.global.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
    		 					   @RequestParam(value = "register", required = false) String register,
                                   @RequestParam(value = "logout", required = false) String logout, Model model) {
        String errorMessge = null;
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        if (register != null) {
            errorMessge = "You have been successfully signed up !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login.html";
    }
    
    @GetMapping(value = "/logout")
    public String displayLogoutPage(HttpServletRequest request , HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication() ; 
        if(auth != null) {
        	new SecurityContextLogoutHandler().logout(request, response, auth)  ; 
        }
        return "redirect:/login?logout=true";
    }

}