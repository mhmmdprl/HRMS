package com.kodlamaio.hrms.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.ChangePaswordRequest;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.UserService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@PutMapping("changePassword")
	public Result changePassword(@Valid @RequestBody ChangePaswordRequest changePaswordRequest,
			HttpServletRequest httpServletRequest) {

		return this.userService.changePassword(changePaswordRequest,
				this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
}
