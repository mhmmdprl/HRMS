package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.model.LoginRequest;
import com.kodlamaio.hrms.result.Result;

public interface LoginService {

	Result login(LoginRequest loginRequest); 
}
