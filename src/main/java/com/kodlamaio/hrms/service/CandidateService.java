package com.kodlamaio.hrms.service;

import java.util.List;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.result.DataResult;

public interface CandidateService  extends BaseService<Candidate>{

	public boolean existsEmployeeByEmail(String email);

	public DataResult<List<Candidate>> findAll();
	
	
}
