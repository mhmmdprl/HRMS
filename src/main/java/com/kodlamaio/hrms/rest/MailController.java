package com.kodlamaio.hrms.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.MailRequest;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.EmailService;
@RestController
@RequestMapping("api/mail")
@CrossOrigin
public class MailController {

	@Autowired
	private EmailService mailService;
	@PostMapping("/sendMail")
	public Result sendMail(@Valid @RequestBody MailRequest mailRequest) {
		
		return this.mailService.sendMail(mailRequest);
	}
}
