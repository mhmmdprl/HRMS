package com.kodlamaio.hrms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kodlamaio.hrms.entities.JobTitle;
import com.kodlamaio.hrms.model.JobTitleRequest;
import com.kodlamaio.hrms.repository.JobTitleRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.JobTitleService;

@Service
public class JobTitleServiceImpl implements JobTitleService {

	@Autowired
	private JobTitleRepository jobTitleRepository;

	@Override
	public Result save(JobTitleRequest jobTitleRequest) {
		JobTitle jobTitle = null;
		try {
			if (this.jobTitleRepository.existsByTitle(jobTitleRequest.getTitle())) {
				return new ErrorResult(jobTitleRequest.getTitle() + " pozisyonu sistemde zaten mevcut");
			}
			jobTitle = new JobTitle();
			jobTitle.setTitle(jobTitleRequest.getTitle());
			this.jobTitleRepository.save(jobTitle);

		} catch (Exception e) {
			return new ErrorResult(e.getMessage());
		}
		return new SuccessResult("Pozisyon başarılı bir şekilde eklendi.");
	}

	@Override
	public DataResult<List<JobTitle>> getAll() {
		return new SuccessDataResult<List<JobTitle>>(this.jobTitleRepository.findAll());
	}

	@Override
	public JobTitle findById(Long jobTitleId) {
		return this.jobTitleRepository.findById(jobTitleId).get();
	}

	@Override
	public JobTitle findByTitle(String jobTitleName) {
		
		return this.jobTitleRepository.findByTitle(jobTitleName);
	}

}
