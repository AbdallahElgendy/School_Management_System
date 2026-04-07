package com.global.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.global.model.Person;
import com.global.model.Role;
import com.global.repository.PersonRepository;

@Component
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private PersonRepository personRepository ; 
	@Autowired
	private PasswordEncoder passwordEncoder ; 
	
	@Override
	public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {	
		String email = authentication.getName() ; 
		String pwd = authentication.getCredentials().toString() ; 
		Person person = personRepository.readByEmail(email)  ; 
		if(null != person && (person.getId() > 0) && (passwordEncoder.matches(pwd, person.getPwd()))) {
			return new UsernamePasswordAuthenticationToken(email, pwd, getGrantedAuthorities(person.getRole()) )  ; 
		}
		else {
			throw new BadCredentialsException("Bad credentials !!!!!!!!") ; 
		}
	}
	
	List<GrantedAuthority> getGrantedAuthorities( Role role){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))) ; 
		return grantedAuthorities ; 
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
