package com.sdrc.covid19odisha.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class SendMailServiceImpl implements SendMailService {

	@Autowired
	private ConfigurableEnvironment configurableEnvironment;

	@Autowired
	public JavaMailSender emailSender;

	@Override
	//@StreamListener(value = CollectionChannel.RMNCHAEMAIL_INPUTCHANNEL)
	public void sendSimpleMessage(MailModel mail) {
		try {

			SimpleMailMessage message = new SimpleMailMessage();

			message.setTo(mail.getEmail());
			message.setSubject(mail.getSubject());
			message.setText(mail.getToUserName() + "\n" + mail.getMessage() + "\n" + mail.getFromUserName());

			emailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
