package com.devilepsy.redditclone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.devilepsy.redditclone.exceptions.SpringRedditException;
import com.devilepsy.redditclone.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class MailService {
	
	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;
	
	@Async // At this point, brought down time from 10.5 sec to 1.9 sec
	 void sendMail(NotificationEmail notificationEmail) throws SpringRedditException 
	 	{
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@gmail.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody())); // returns message in html format
			
		};
		
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent !!");
		}catch(MailException e){
			
			throw new SpringRedditException("Exception occured while sending mail to " + notificationEmail.getRecipient());
			
		}
		
	}

}
