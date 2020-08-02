package com.devilepsy.redditclone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long userId;
	
	@NotBlank(message="Username required")
	private String username;
	
	@NotBlank(message="Password required")
	private String password;
	
	@Email
	@NotEmpty(message="Email is required")
	private String email;
	
	private Instant created;
	
	private boolean enabled; // user enabled after email verification process

}
