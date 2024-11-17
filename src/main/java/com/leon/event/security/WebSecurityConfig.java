package com.leon.event.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.leon.event.config.AuthenticationFailureHandler;
import com.leon.event.config.AuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig {

	@Autowired 
	private AuthenticationFailureHandler failureHandler;
	@Autowired 
	private AuthenticationSuccessHandler successHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
        	.csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/").permitAll()
            		.requestMatchers("/view").permitAll()
            		.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .formLogin(form -> form
            		.loginPage("/admin/login").permitAll()
            		.successHandler(successHandler)
            		.failureHandler(failureHandler))
            .logout(logout -> logout
            		.invalidateHttpSession(true)
            		.logoutSuccessUrl("/"));
        
		return http.build();
		
	}
	
	@Bean
	public UserDetailsService userDetails() {
		
		UserDetails user = User.builder().username("user@user.com")
				.password(passwordEncoder().encode("password"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user);
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
