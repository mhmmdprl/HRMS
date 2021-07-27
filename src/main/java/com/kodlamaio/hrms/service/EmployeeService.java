package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.entities.Employee;
import com.kodlamaio.hrms.result.Result;

public interface EmployeeService {

	
	public Result comfirmEmployer(String email);

	public boolean existsEmployeeById(Long id);

	public Employee findByEmployeeId(Long userIdFromRequest); 
}
