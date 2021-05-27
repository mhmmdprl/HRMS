package com.kodlamaio.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.User;
import com.kodlamaio.hrms.model.LoginRequest;
import com.kodlamaio.hrms.repository.UserRepository;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Result login(LoginRequest loginRequest) {
		
		if (!this.userRepository.existsByEmail(loginRequest.getEmail())) {

			return new ErrorResult("Email Hatalı ");
		}
		User user = this.userRepository.findByEmail(loginRequest.getEmail());
		if (!user.getPassword().equals(loginRequest.getPassword())) {

			return new ErrorResult("Şifre hatalı");

		}
		if (!user.isAcctive()) {

			return new ErrorResult("Üyeliğiniz aktif edilmemiştir");
		}

		return new  SuccessResult("Başarılı bir şekilde giriş yapıldı.");
	}

}
