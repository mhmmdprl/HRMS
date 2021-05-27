package com.kodlamaio.hrms.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.model.EmployerRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.EmployerService;

@RestController
@RequestMapping("/employe")
public class EmployerController {

	@Autowired
	private EmployerService employerService;
 	
	@PostMapping("/save")
	public Result saveEmployer(@RequestBody EmployerRequest employerRequest) {
		return this.employerService.save(employerRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<Employer>> getAll(){
		
		
		return this.employerService.getAll();
	}
}
