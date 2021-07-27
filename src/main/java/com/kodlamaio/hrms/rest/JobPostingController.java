package com.kodlamaio.hrms.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.JobPostingListRequest;
import com.kodlamaio.hrms.model.JobPostingSaveRequest;
import com.kodlamaio.hrms.model.JobPostingUpdateRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.JobPostingService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/jobposting/")
@CrossOrigin
public class JobPostingController {

	@Autowired
	private JobPostingService jobPostingService;
	
	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("save")
	public Result save(@Valid @RequestBody JobPostingSaveRequest jobPostingRequest,
			HttpServletRequest httpServletRequest) {
        System.out.println(jobPostingRequest.isCvMandatory());
		return this.jobPostingService.save(jobPostingRequest, httpServletRequest);

	}
	@PutMapping("update")
	public Result update(@Valid @RequestBody JobPostingUpdateRequest jobPostingRequest,
			HttpServletRequest httpServletRequest) {

		return this.jobPostingService.update(jobPostingRequest, httpServletRequest);

	}
     
	@GetMapping("getByJobPosition")
	public DataResult<Page<JobPostingListRequest>> getByJobPosition(@RequestParam String searchingWord,
			@RequestParam int page, @RequestParam int size){
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC ,"createdDate"));
				return this.jobPostingService.getByJobPosition(searchingWord,pageable);
		
	}
	@GetMapping("/getall")
	public DataResult<Page<JobPostingListRequest>> getAllActive(@RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC ,"createdDate"));

		return this.jobPostingService.getAll(pageable);
	}

	@PutMapping("/activePassive")
	public Result activePassive(@RequestParam Long jobPositngId) {
		return this.jobPostingService.activePassive(jobPositngId);

	}

	@GetMapping("getJobPostingActiveOrPassive")
	public DataResult<Page<JobPostingListRequest>> getAllActiveOrPassive(@RequestParam int page, @RequestParam int size,
			@RequestParam boolean status, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC ,"createdDate"));

		return this.jobPostingService.findActiveOrPassiveJobPosting(this.tokenProvider.getUserIdFromRequest(httpServletRequest), status, pageable);
	}
	@GetMapping("getAllJobPostinById")
	public DataResult<Page<JobPostingListRequest>> getAllJobPostinById(@RequestParam int page, @RequestParam int size,
			@RequestParam Long id, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC ,"createdDate"));

		return this.jobPostingService.findActiveOrPassiveJobPosting(id, true, pageable);
	}
	@DeleteMapping("delete")
	public Result deleteJobPosting(@RequestParam Long id,HttpServletRequest httpServletRequest) {
		
		return this.jobPostingService.deleteJobPosting(id);
	}
	@GetMapping("getInterestingJobPostings")
	public DataResult<Page<JobPostingListRequest>>  getInterestingJobPostings(@RequestParam int page, @RequestParam int size,HttpServletRequest httpServletRequest){
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC ,"createdDate"));
		
		return this.jobPostingService.getInterestingJobPostings(pageable,this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
	
	@GetMapping("getFollowedCompanysJobPosting")
	public DataResult<Page<JobPostingListRequest>> getFollowedCompanysJobPosting(@RequestParam int page, @RequestParam int size,HttpServletRequest httpServletRequest){
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC ,"createdDate"));
		
	 return	this.jobPostingService.getFollowedCompanysJobPosting(this.tokenProvider.getUserIdFromRequest(httpServletRequest),pageable);
	}
	@PostMapping("/search")
	public DataResult<Page<JobPostingListRequest>> searchCandidate(@RequestBody ListRequest listRequest,
			@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		listRequest.setPageable(pageable);
		return this.jobPostingService.searchJobPosting(listRequest);
	}

}
