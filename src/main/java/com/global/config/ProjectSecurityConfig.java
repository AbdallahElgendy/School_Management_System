package com.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		http.csrf((csrf)->csrf.ignoringRequestMatchers("/saveMsg").ignoringRequestMatchers("/data-api/**")
				.ignoringRequestMatchers("/swagger-ui/**","/v3/**")
				.ignoringRequestMatchers("/public/**").ignoringRequestMatchers("/api/**"))
				.authorizeHttpRequests((authorize)->authorize
				.requestMatchers("/dashboard").authenticated()
				.requestMatchers("/displayMessages/**").hasRole("ADMIN")
				.requestMatchers("/closeMsg/**").hasRole("ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/student/**").hasRole("STUDENT")
				.requestMatchers("/displayProfile").authenticated()
				.requestMatchers("/api/**").authenticated()
				.requestMatchers("/updateProfile").authenticated()
				.requestMatchers("/data-api/**").authenticated()
				.requestMatchers("/swagger-ui/**","/v3/**").authenticated()
				.requestMatchers("/" , "/home").permitAll()
				.requestMatchers("/holidays/**").permitAll()
				.requestMatchers("/contact").permitAll()
				.requestMatchers("/courses").permitAll()
				.requestMatchers("/saveMsg").permitAll()
				.requestMatchers("/about").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/logout").permitAll()
				.requestMatchers("/public/**").permitAll()
				.requestMatchers("/assets/**").permitAll())
		.formLogin((login)->login.loginPage("/login").defaultSuccessUrl("/dashboard")
				.failureUrl("/login?error=true").permitAll())
		.logout((logout)->logout.logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true).permitAll()).httpBasic(Customizer.withDefaults()) ; 
		return http.build();	
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();  // ✅ strong encoding
	}
}
