package com.kodlamaio.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.repository.EmployerRepository;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployerRepository employerRepository;

	@Override
	public Result comfirmEmployer(String email) {
		Employer employer = null;
		if (this.employerRepository.existsByEmail(email)) {
			employer = this.employerRepository.findByEmail(email);
			if (employer.isAcctive()) {
				employer.setManagerConfirm(true);
				this.employerRepository.save(employer);
				return new SuccessResult("Aktivasyon işlemi başarılı.");
			}
			return new ErrorResult("Şirket henüz email aktivasyon yapmamış.");
		}
		return new ErrorResult("Aktivasyon işlemi başarısız. " + email + " bulunmadım");
	}

}
