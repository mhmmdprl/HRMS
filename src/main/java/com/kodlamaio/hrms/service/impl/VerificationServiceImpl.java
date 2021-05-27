package com.kodlamaio.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.User;
import com.kodlamaio.hrms.entities.Verification;
import com.kodlamaio.hrms.repository.UserRepository;
import com.kodlamaio.hrms.repository.VerificationRepository;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.VerificationService;
@Service
public class VerificationServiceImpl implements VerificationService {

	@Autowired
	private VerificationRepository verificationRepository;

	@Autowired
	private UserRepository userRepository;
	@Override
	public Verification save(Verification verification) {
		return this.verificationRepository.save(verification);
	}

	@Override
	public Verification findByVerificationCode(String verificationCode) {
		
		return this.verificationRepository.findByVerificationCode(verificationCode);
	}

	@Override
	public Result activation(String verificationCode) {
		Verification verification = this.findByVerificationCode(verificationCode);
		if (verification == null) {
			return new ErrorResult("Geçersiz aktivasyon işlemi!");
		}
		User user=this.userRepository.findById(verification.getUserId()).orElseThrow();
		user.setAcctive(true);
		this.userRepository.save(user);
		verification.setDeleted('1');
		this.verificationRepository.save(verification);
		return new SuccessResult("Aktivasyon işleminiz başarılı bir şekilde gerçekleşmiştir.");

	}
	

}
