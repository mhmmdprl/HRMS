package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.model.MailRequest;
import com.kodlamaio.hrms.result.Result;

public interface EmailService {

	void sendSimpleMessage(String to, String subject, String text);
	
	Result sendMail(MailRequest mailRequest);
}
