package com.kodlamaio.hrms.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class EmployerRequest {

	@Email(message = "email formatına uygun girmelisiniz")
	private String email;
	
	@Size(min = 6,max = 14,message = "6 ile 14 karakter arasında şifre belirleyiniz")
	@NotBlank
	private String password;
	@NotBlank
	private String companyName;
	@NotBlank
	private String webAdress;
	@NotBlank
	private String phoneNumber;
	
}
