package com.kodlamaio.hrms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.EmployeeService;

@RequestMapping("/employee")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PutMapping("/comfirm")
	public Result comfirmEmployer(@RequestParam String email) {
		return this.employeeService.comfirmEmployer(email);
	}
	
	

}
