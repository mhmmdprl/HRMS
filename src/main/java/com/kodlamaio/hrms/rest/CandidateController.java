package com.kodlamaio.hrms.rest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.model.EmployeeRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.service.CandidateService;

@RestController
@RequestMapping("/candidate")
public class CandidateController{

	@Autowired
	private CandidateService candidateService;
	
	@PostMapping("/save")
	public DataResult<Candidate> saveEmployee(@RequestBody EmployeeRequest employeeRequest){
	
		Candidate employee=new Candidate();
		employee.setName(employeeRequest.getName());
		employee.setLastName(employeeRequest.getLastName());
		employee.setEmail(employeeRequest.getEmail());
		employee.setPassword(employeeRequest.getPassword());
		employee.setBirtOfDate(employeeRequest.getBirtOfDate());
		employee.setIdentityNumber(employeeRequest.getIdentityNumber());

		
		return this.candidateService.save(employee);
	}
	
	@GetMapping("/getAll")
	public  DataResult<List<Candidate>> getAll(){
		
		return this.candidateService.findAll();
	}
	
	
}
