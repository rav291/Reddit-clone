package com.devilepsy.redditclone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.devilepsy.redditclone.exceptions.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.Jwts.parser;



@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void init()
	{
		try {
			keyStore = KeyStore.getInstance("JKS");
		
		InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
		keyStore.load(resourceAsStream, "ravianand".toCharArray());
		
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
	
			throw new SpringRedditException("Exception occured while loading keystore");
		}
	}
	
	public String generateToken(Authentication authentication)
	{
		org.springframework.security.core.userdetails.User principal = (User)authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.compact(); // for converting, token to string
		
		// We are using asymmetric encryption : key comes in pair... 
	}

	private PrivateKey getPrivateKey() {
		
		try {
			return (PrivateKey) keyStore.getKey("springblog", "ravianand".toCharArray()); // ravianand is the keystore password. 
		} catch ( KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
		
			throw new SpringRedditException("Exception occured while retrieving public key from the keystore");
			
		}
	}
	
	public boolean validateToken(String jwt)
	{
		parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		
		return true;
		
		
		
	}

	private PublicKey getPublicKey() {
		
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		} catch (KeyStoreException e) {
			
			throw new SpringRedditException("Exception occured while retrieving public key"
											+ " from keystore");
			
		}
				
	}
	
	public String getUsernameFromJwt(String token)
	{
		Claims claims = parser().setSigningKey(getPublicKey())
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}

	
}
