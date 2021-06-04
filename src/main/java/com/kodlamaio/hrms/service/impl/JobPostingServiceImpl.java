package com.kodlamaio.hrms.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.JobPosting;
import com.kodlamaio.hrms.model.JobPostingListRequest;
import com.kodlamaio.hrms.model.JobPostingSaveRequest;
import com.kodlamaio.hrms.repository.JobPostingRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CityService;
import com.kodlamaio.hrms.service.EmployerService;
import com.kodlamaio.hrms.service.JobPostingService;
import com.kodlamaio.hrms.service.JobTitleService;

@Service
public class JobPostingServiceImpl implements JobPostingService {

//	@Autowired
//	private ModelMapper modelMapper;
	@Autowired
	private JobPostingRepository jobPostingRepository;

	@Autowired
	private CityService cityService;
	@Autowired
	private EmployerService employerService;

	@Autowired
	private JobTitleService jobTitleService;

	@Override
	public DataResult<JobPostingSaveRequest> save(JobPostingSaveRequest jobPostingRequest) {
		JobPosting jobPosting = null;
		try {

			jobPosting = new JobPosting();
			jobPosting.setMaxSalary(jobPostingRequest.getMaxSalary());
			jobPosting.setMinSalary(jobPostingRequest.getMinSalart());
			jobPosting.setNumberOfAvailablePosition(jobPostingRequest.getNumberOfAvailablePosition());
			jobPosting.setEmployer(this.employerService.findById(jobPostingRequest.getEmployerId()));
			jobPosting.setCity(this.cityService.findByCityName(jobPostingRequest.getCityName()));
			jobPosting.setApplicationDeadline(jobPostingRequest.getApplicationDeadline());
			jobPosting.setJobTitle(this.jobTitleService.findByTitle(jobPostingRequest.getJobTitleName()));
			this.jobPostingRepository.save(jobPosting);
		} catch (Exception e) {
			return new ErrorDataResult<>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<>("Kayıt işlemi başarılı");
	}

	@Override
	public DataResult<Page<JobPostingListRequest>> getAll(Pageable pageable) {
		Page<JobPosting> jobPostings = this.jobPostingRepository.findAll(pageable);
		if (jobPostings.getContent().size() < 1) {
			return new ErrorDataResult<Page<JobPostingListRequest>>("İş ilanı bulunamadı!");
		}
		Page<JobPostingListRequest> jobPostingListRequests = new PageImpl<JobPostingListRequest>(jobPostings.stream()
				.map(item -> new JobPostingListRequest(item.getMaxSalary(), item.getMinSalary(),
						item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
						item.getEmployer().getCompanyName(), item.getJobTitle().getTitle(),
						item.getCity().getCityName())

				).collect(Collectors.toList()), pageable, jobPostings.getTotalElements());

		return new SuccessDataResult<Page<JobPostingListRequest>>(jobPostingListRequests);

	}

	@Override
	public Result activePassive(Long jobPostingId) {
		JobPosting jobPosting=null;
		try {
			jobPosting=this.jobPostingRepository.findById(jobPostingId).get();
			if(jobPosting.isActive()) {
				jobPosting.setActive(false);
				this.jobPostingRepository.save(jobPosting);
				return new SuccessResult("İş ilanı pasifleştirildi!");
			}
			
			jobPosting.setActive(true);
			this.jobPostingRepository.save(jobPosting);
		
			
		} catch (Exception e) {
			return new ErrorResult("Hata : "+e.getMessage());
		}
		return new SuccessResult("İş ilanı aktifleştirildi!");
	}

}
