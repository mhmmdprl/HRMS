package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.entities.Verification;
import com.kodlamaio.hrms.result.Result;

public interface VerificationService {
	
	public Verification save(Verification companyVerification);
	
	public Verification findByVerificationCode(String verificationCode);

	public Result activation(String verificationCode); 
	
	public boolean validateToken(Verification verification);

	public Verification findByUserId(Long id);
	
	
	
}
