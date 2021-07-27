package com.kodlamaio.hrms.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.ResetPasswordRequest;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.PasswordResetTokenService;

@RestController
@RequestMapping("api/passwordResetToken")
@CrossOrigin
public class PasswordResetTokenController {

	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	@PostMapping("createToken")
	public Result createToken(@RequestParam("email") final String email,final HttpServletRequest request ) {
		return this.passwordResetTokenService.create(email);
	}
	
	@PostMapping("checkToken")
	public Result checkToken(@RequestParam("token") final String token,final HttpServletRequest request ) {
		return this.passwordResetTokenService.tokenControl(token);
	}
	
	@PostMapping("resetPassword")
	public Result resetPassword(@RequestBody final ResetPasswordRequest resetPasswordRequest,final HttpServletRequest request ) {
		return this.passwordResetTokenService.resetPassword(resetPasswordRequest);
	}

}
