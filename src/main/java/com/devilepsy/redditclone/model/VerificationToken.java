package com.devilepsy.redditclone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/* Whenever a user registers, we generate a token, store it in the database, through this 
   entity, and send this token as part of the activation link to the user*/

/*When the user clicks that link, we look up the user associated with this token, and 
  enable that user*/

@Data
@NoArgsConstructor
@Entity
@Table(name="token")
public class VerificationToken {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	private String token;
	
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	
	private Instant expiryDate;

}
