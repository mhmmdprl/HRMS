package com.kodlamaio.hrms.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kodlamaio.hrms.model.JobPostingListRequest;
import com.kodlamaio.hrms.model.JobPostingSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface JobPostingService extends BaseService<JobPostingSaveRequest>{

	DataResult<Page<JobPostingListRequest>> getAll(Pageable sortedByName);

	Result activePassive(Long jobPositngId);


}
