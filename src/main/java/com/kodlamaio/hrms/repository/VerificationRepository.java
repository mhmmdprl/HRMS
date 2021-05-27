package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.hrms.entities.Verification;

public interface VerificationRepository  extends JpaRepository<Verification, Long>{

	public Verification findByVerificationCode(String verificationCode);
}
