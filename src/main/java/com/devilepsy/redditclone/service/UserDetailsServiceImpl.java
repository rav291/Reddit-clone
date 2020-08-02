package com.devilepsy.redditclone.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devilepsy.redditclone.model.User;
import com.devilepsy.redditclone.repository.UserRepo;
import static java.util.Collections.singletonList; // import not available
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepo userRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		
		// This methods gives userDetails object on the basis of the username
		
		Optional<User> userOptional = userRepository.findByUsername(username); 
		
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No User found"
				+ "with Username :" + username));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
					user.getPassword(), user.isEnabled(), true, true, true, getAuthorities("USER"));
	}
	
	// implementing the userDetailService inteface

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		
		return singletonList(new SimpleGrantedAuthority(role));
		
	}

	
	
}
