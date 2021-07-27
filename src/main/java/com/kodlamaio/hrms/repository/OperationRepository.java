package com.kodlamaio.hrms.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{

	public Operation findByCode(String code);

	@Query("From Operation o where o.id not in ?1")
	public List<Operation> getRoleHasNotOperations(List<Long> ids);
	@Query("From Operation o where o.id in ?1")
	public List<Operation> getRoleHasOperations(List<Long> ids);

}
