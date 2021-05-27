package com.kodlamaio.hrms.service;

import java.util.List;

import com.kodlamaio.hrms.entities.JobTitle;
import com.kodlamaio.hrms.model.JobTitleRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface JobTitleService {

	Result save(JobTitleRequest jobTitleRequest);

	DataResult<List<JobTitle>> getAll();

}
