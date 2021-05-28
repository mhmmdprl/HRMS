package com.kodlamaio.hrms.service;

import java.util.List;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.model.EmployerRequest;
import com.kodlamaio.hrms.result.DataResult;

public interface EmployerService extends BaseService<EmployerRequest> {

	DataResult<List<Employer>> getAll();
	

}
