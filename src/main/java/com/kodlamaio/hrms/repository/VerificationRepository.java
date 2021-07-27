package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Verification;

public interface VerificationRepository  extends JpaRepository<Verification, Long>{

	@Query("From Verification v where v.deleted='0' and v.verificationCode=?1")
	public Verification findByVerificationCode(String verificationCode);

	public Verification findByUserId(Long id);
}
