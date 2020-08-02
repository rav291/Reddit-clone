package com.devilepsy.redditclone.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {
	
	private TemplateEngine templateEngine;
	
	String build(String message)
	{
		Context context = new Context();
		
		context.setVariable("message", message);
		
		return templateEngine.process("mailTemplate", context);
		
		// At runtime thymleaf automa. adds email message to the html template.
	}

}
