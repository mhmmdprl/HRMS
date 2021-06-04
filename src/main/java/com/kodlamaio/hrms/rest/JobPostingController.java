package com.kodlamaio.hrms.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.JobPostingListRequest;
import com.kodlamaio.hrms.model.JobPostingSaveRequest;
import com.kodlamaio.hrms.model.PageAbleRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.JobPostingService;

@RestController
@RequestMapping("/jobposting/")
public class JobPostingController {

	@Autowired
	private JobPostingService jobPostingService;
	
	@PostMapping
	public DataResult<JobPostingSaveRequest> save(@RequestBody JobPostingSaveRequest jobPostingRequest){
	
		return this.jobPostingService.save(jobPostingRequest);
		
	}
	
	@PutMapping("/getall")
	public DataResult<Page<JobPostingListRequest>> getAll(@RequestBody PageAbleRequest pageRequest){
		
		Pageable sorted= PageRequest.of(pageRequest.getMinPage(), pageRequest.getMaxPage(),Sort.by(pageRequest.getSortBy()));
		
		return this.jobPostingService.getAll(sorted);
	}
	
	@PutMapping("/activePassive")
	public Result activePassive(@RequestParam Long jobPositngId) {
		return this.jobPostingService.activePassive(jobPositngId);
		
		
	}
	
}
