package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.entities.User;
import com.kodlamaio.hrms.model.ChangePaswordRequest;
import com.kodlamaio.hrms.result.Result;

public interface UserService {

	public User findByEmail(String email);

	public boolean existsByEmailAndDeleted(String email);

	public void save(User user);
	
	Result changePassword(ChangePaswordRequest changePasswordRequest,Long userIdFromRequest);

	public User findById(Long id);
}
