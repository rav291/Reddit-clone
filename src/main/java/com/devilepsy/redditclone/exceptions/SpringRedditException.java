package com.devilepsy.redditclone.exceptions;


@SuppressWarnings("serial")
public class SpringRedditException extends RuntimeException {

	public SpringRedditException(String exMessage)
	{
		super(exMessage);
	}
	
}

// whenever an exception occurs we don't want to expose technical details to the user.
