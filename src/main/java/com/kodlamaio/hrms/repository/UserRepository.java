package com.kodlamaio.hrms.repository;

import com.kodlamaio.hrms.entities.User;

public interface UserRepository extends BaseRepository<User>{

	User findByEmail(String email);
	
	boolean existsByEmail(String email);

	boolean existsByEmailAndDeleted(String email, char c);
	
}
