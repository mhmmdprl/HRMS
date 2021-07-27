package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.PasswordResetToken;
import com.kodlamaio.hrms.entities.User;

public interface PasswordResetTokenRepository  extends JpaRepository<PasswordResetToken, Long>{

	@Query("From PasswordResetToken prt where prt.deleted='0' and prt.user=?1")
	PasswordResetToken findByUser_Id(User user);

	boolean existsByTokenAndDeleted(String token, char c);

	PasswordResetToken findByTokenAndDeleted(String token, char c);

}
