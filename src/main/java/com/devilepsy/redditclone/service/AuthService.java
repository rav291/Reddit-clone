package com.devilepsy.redditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devilepsy.redditclone.DTO.AuthenticationResponse;
import com.devilepsy.redditclone.DTO.LoginRequest;
import com.devilepsy.redditclone.DTO.RegisterRequest;
import com.devilepsy.redditclone.exceptions.SpringRedditException;
import com.devilepsy.redditclone.model.NotificationEmail;
import com.devilepsy.redditclone.model.User;
import com.devilepsy.redditclone.model.VerificationToken;
import com.devilepsy.redditclone.repository.UserRepo;
import com.devilepsy.redditclone.repository.VerificationTokenRepo;
import com.devilepsy.redditclone.security.JwtProvider;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
// since we're interacting with a relational database.
public class AuthService {
	
	
	/*@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepository; --> This is field injection, however,
	Spring recommends constructor injection*/
	
	private final PasswordEncoder passwordEncoder; // Lombok handles constructor injection, 
	private final UserRepo userRepository;         // because of final, @AllArgsConstructor annotation.
 	private final VerificationTokenRepo verificationTokenRepository;
 	private final MailService mailService;
 	private final AuthenticationManager authenticationManager;
 	private final JwtProvider jwtProvider;
	
	@Transactional
	public void signup(RegisterRequest registerRequest)
	{
		
		User user = new User();
		
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		
		user.setCreated(Instant.now());
		user.setEnabled(false);  // So that we can enable the user after email verification.
		
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),
				"Thank you for signing up to Spring Reddit,"
			  + "Please click on the below url to activate your account : " + 
				"http://localhost:8080/api/auth/accountVerification/"  + token));
		
		// Whenever the user clicks on the url, we take the token from the URL, look it up in the DB,
		// fetch the user who enabled this token, and enable that user.
		
	}


	private String generateVerificationToken(User user) {
		
		String token = UUID.randomUUID().toString(); // generates unique, random 128 bit value, for verification token
		
		VerificationToken verificationToken = new VerificationToken();
		
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		
		return token;
		
	}


	public void verifyAccount(String token){
		
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
		
		// Optional is a util class, which has method orElseThrow(), look up.	
		
		fetchUserAndEnable(verificationToken.get());
	

   }

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not Found with name :" + username));
		
		user.setEnabled(true);
		userRepository.save(user);
		
		
	}


	public AuthenticationResponse login(LoginRequest loginRequest) {
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authenticate); // storing authentication object in SecurityContextHolder
		String token = jwtProvider.generateToken(authenticate);
		
		return new AuthenticationResponse(token, loginRequest.getUsername());
		
	}
	
}
