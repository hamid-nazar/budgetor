package com.budget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	
	private final JavaMailSender javaMailSender;

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendEmailNotification(String to, String from, String subject, String message) {

		SimpleMailMessage simpleMessage = new SimpleMailMessage();

		simpleMessage.setTo(to);
		simpleMessage.setFrom(from);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(message);

		javaMailSender.send(simpleMessage);

	}
}
