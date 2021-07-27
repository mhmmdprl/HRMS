package com.kodlamaio.hrms.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kodlamaio.hrms.entities.JobPosting;
import com.kodlamaio.hrms.model.JobPostingListRequest;
import com.kodlamaio.hrms.model.JobPostingSaveRequest;
import com.kodlamaio.hrms.model.JobPostingUpdateRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface JobPostingService {

	DataResult<Page<JobPostingListRequest>> getAll(Pageable sortedByName);

	Result activePassive(Long jobPositngId);

	Result save(JobPostingSaveRequest jobPostingSaveRequest, HttpServletRequest httpServletRequest);

	DataResult<Page<JobPostingListRequest>> findActiveOrPassiveJobPosting(Long id, boolean status, Pageable pageable);

	Result update( JobPostingUpdateRequest jobPostingRequest, HttpServletRequest httpServletRequest);

	Result deleteJobPosting(Long id);

	JobPosting findById(Long id);

	void saveForOtherServices(JobPosting jobPosting);

	DataResult<Page<JobPostingListRequest>> getInterestingJobPostings(Pageable pageable,Long id);

	DataResult<Page<JobPostingListRequest>> getFollowedCompanysJobPosting(Long userIdFromRequest, Pageable pageable);

	DataResult<Page<JobPostingListRequest>> searchJobPosting(ListRequest listRequest);

	DataResult<Page<JobPostingListRequest>> getByJobPosition(String searchingWord, Pageable pageable);

	int getAllCount();

	DataResult<Page<JobPostingListRequest>> getCandidateJobPosting(Long id, Pageable pageable);

	DataResult<Page<JobPostingListRequest>> getEmployerJobPosting(Long id, Pageable pageable);

	

}
