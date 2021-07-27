package com.kodlamaio.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.model.MailRequest;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.EmailService;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender javaMailSender;
	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
	
		try {
			SimpleMailMessage mailMessage=new SimpleMailMessage();
			mailMessage.setTo(to);
			mailMessage.setFrom("mhmmdprl@gmail.com");
			mailMessage.setText(text);
			mailMessage.setSubject(subject);
			javaMailSender.send(mailMessage);
		} catch (Exception e) {
			log.error("Email gönderilemedi {}"+e);
			
		}
		
	}
	@Override
	public Result sendMail(MailRequest mailRequest) {
		SimpleMailMessage mailMessage=null;
		try {
			mailMessage=new SimpleMailMessage();
			mailMessage.setFrom(mailRequest.getFrom());
			mailMessage.setTo(mailRequest.getTo());
			mailMessage.setText(mailRequest.getText());
			mailMessage.setSubject(mailRequest.getSubject());
			this.javaMailSender.send(mailMessage);
		} catch (Exception e) {
			return new ErrorResult("Mail gönderilemedi");
		}
		return new SuccessResult("Mail gönderildi");
	}
	

}
