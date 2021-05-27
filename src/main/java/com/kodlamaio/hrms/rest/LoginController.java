package com.kodlamaio.hrms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.LoginRequest;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public Result login(@RequestBody LoginRequest loginRequest) {
		
		return this.loginService.login(loginRequest);
	}
}
