package com.kodlamaio.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Natification;

public interface NatificationRepository extends JpaRepository<Natification, Long> {

	
	
	List<Natification> findByToWho_Id(Long userIdFromRequest);

	@Query("From Natification n where n.seen=false and n.toWho=?1")
	List<Natification> getHaventSeenNatification(Candidate candidate);

	@Query("Select Count(n) From Natification n where n.seen=false and n.toWho=?1")
	int getHaventSeenNatificationCount(Candidate candidate);

}
