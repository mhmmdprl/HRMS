package com.kodlamaio.hrms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

	private String password;
	
	private String rePassword;
	
	private String token;
}
