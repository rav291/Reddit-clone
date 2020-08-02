package com.devilepsy.redditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devilepsy.redditclone.DTO.AuthenticationResponse;
import com.devilepsy.redditclone.DTO.LoginRequest;
import com.devilepsy.redditclone.DTO.RegisterRequest;
import com.devilepsy.redditclone.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) 
	{
		authService.signup(registerRequest);
		
		return new ResponseEntity<>("User registration successful", HttpStatus.OK); // httpstatus enum
		
	}
	
	@GetMapping("accountVerification/{token}")  // endpoint, after user clicks on the activation link
	public ResponseEntity<String> verifyAccount(@PathVariable String token)  // pathVariable grabs the above token
	{
		authService.verifyAccount(token);
		
		return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
		
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
	{
		return authService.login(loginRequest);
		
	}

}
