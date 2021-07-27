package com.kodlamaio.hrms.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.entities.JobTitle;
import com.kodlamaio.hrms.model.JobTitleRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.JobTitleService;

@RestController
@RequestMapping("api/jobtitle")
@CrossOrigin
public class JobTitleController {

	@Autowired
	private JobTitleService jobTitleService;
	@PostMapping("/save")
	public Result saveJobTitle(@RequestBody JobTitleRequest jobTitleRequest) {
		
		
		return this.jobTitleService.save(jobTitleRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<JobTitle>> getAll(){
		
		
		return this.jobTitleService.getAll();
	}
}
