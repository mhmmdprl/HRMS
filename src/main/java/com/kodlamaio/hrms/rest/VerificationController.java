package com.kodlamaio.hrms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.VerificationService;

@RequestMapping("/activation")
@RestController
public class VerificationController {

	@Autowired
	private VerificationService verificaitonService;

	@GetMapping
	public Result activationMember(@RequestParam String token) {
		return this.verificaitonService.activation(token);
	}
}
