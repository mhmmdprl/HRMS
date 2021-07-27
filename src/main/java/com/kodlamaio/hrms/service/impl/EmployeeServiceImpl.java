package com.kodlamaio.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Employee;
import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.repository.EmployeeRepository;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.EmployeeService;
import com.kodlamaio.hrms.service.EmployerService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployerService employerService;
	@Autowired 
	private EmployeeRepository employeeRepository;

	@Override
	public Result comfirmEmployer(String email) {
		Employer employer = null;
		if (this.employerService.existsByEmail(email)) {
			employer = this.employerService.findByEmail(email);
			employer.setManagerConfirm(true);
			this.employerService.saveForOtherService(employer);
			return new SuccessResult("Aktivasyon işlemi başarılı.");

		}
		return new ErrorResult("Aktivasyon işlemi başarısız. " + email + " bulunamadı");
	}

	@Override
	public boolean existsEmployeeById(Long id) {
		return this.employeeRepository.existsById(id);
	}

	@Override
	public Employee findByEmployeeId(Long userIdFromRequest) {
	
		return this.employeeRepository.findById(userIdFromRequest).orElseThrow();
	}

}
