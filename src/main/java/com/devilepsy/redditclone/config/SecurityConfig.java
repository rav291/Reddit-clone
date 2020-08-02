package com.devilepsy.redditclone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devilepsy.redditclone.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailsService; // loads userdata from various sources,
														 //  db in our case
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)     // Contains globally used default Bean IDs for beans created by the namespace support in Spring Security 2.
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		
		return super.authenticationManagerBean();
		
	}
	
	// Disabling CSRF attack protection, since we're using JWT, rest apis are stateless
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception
	{
		
		httpSecurity.csrf().disable().authorizeRequests()
		.antMatchers("/api/auth/**").permitAll()
		.anyRequest().authenticated();
		
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, 
					UsernamePasswordAuthenticationFilter.class);
		
		// Spring tries to first check for the access token (the jwt token) before trying the
		// usernamePassword authentication scheme.
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception 
	{
		
		authenticationManagerBuilder.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
		
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		
		return new BCryptPasswordEncoder();
		
	}	
	
}
