package com.devilepsy.redditclone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob; // https://www.objectdb.com/api/java/jpa/Lob 
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.sun.istack.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long postId;	
	
	@NotBlank(message= "Ek jhapaad mein 3 daant dheele kar deta hun main")
	private String postName;
	
	@Nullable
	private String url;
	
	@Nullable
	@Lob
	private String description;
	
	private Integer voteCount;
	
	@ManyToOne(fetch= FetchType.LAZY) // postToUser
	@JoinColumn(name= "userId", referencedColumnName= "userId")
	private User user;
	
	private Instant createdDate;
	
	@ManyToOne(fetch= FetchType.LAZY) // postToSubreddit
	@JoinColumn(name= "id", referencedColumnName= "id")
	private Subreddit subreddit;

}

// Lazy loading: https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
