package com.kodlamaio.hrms.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;

@RestController
@RequestMapping("/api/test")
public class TetstController {

	
	@GetMapping("/testapi")
	public Result testApi() {
		return new SuccessResult("API aktif");
	}
}
