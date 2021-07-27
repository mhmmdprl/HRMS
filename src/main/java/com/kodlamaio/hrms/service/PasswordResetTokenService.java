package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.entities.PasswordResetToken;
import com.kodlamaio.hrms.model.ResetPasswordRequest;
import com.kodlamaio.hrms.result.Result;

public interface PasswordResetTokenService {
	
	Result  create(String email);
	
	Result tokenControl(String token);
	
	public boolean validatePasswordResetToken(PasswordResetToken passwordResetToken); 
	
	public Result resetPassword(ResetPasswordRequest resetPasswordRequest);

}
