package com.kodlamaio.hrms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.entities.JobPosting;
import com.kodlamaio.hrms.model.JobPostingListRequest;
import com.kodlamaio.hrms.model.JobPostingSaveRequest;
import com.kodlamaio.hrms.model.JobPostingUpdateRequest;
import com.kodlamaio.hrms.model.LikesRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.model.PostApplicationsRequest;
import com.kodlamaio.hrms.repository.JobPostingRepository;
import com.kodlamaio.hrms.repository.specification.JobPostingSpecification;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.CityService;
import com.kodlamaio.hrms.service.EmployerService;
import com.kodlamaio.hrms.service.JobPostingService;
import com.kodlamaio.hrms.service.JobTitleService;
import com.kodlamaio.hrms.util.TokenProvider;

@Service
public class JobPostingServiceImpl implements JobPostingService {

	@Autowired
	private JobPostingRepository jobPostingRepository;

	@Autowired
	private CityService cityService;
	@Autowired
	private EmployerService employerService;

	@Autowired
	private JobTitleService jobTitleService;
	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CandidateService candidateService;

	@Override
	public Result save(JobPostingSaveRequest jobPostingRequest, HttpServletRequest httpServletRequest) {
		JobPosting jobPosting = null;
		try {

			jobPosting = new JobPosting();
			jobPosting.setMaxSalary(jobPostingRequest.getMaxSalary());
			jobPosting.setMinSalary(jobPostingRequest.getMinSalary());
			jobPosting.setPostSummary(jobPostingRequest.getPostSummary());
			jobPosting.setNumberOfAvailablePosition(jobPostingRequest.getNumberOfAvailablePosition());
			jobPosting.setEmployer(
					this.employerService.getById(this.tokenProvider.getUserIdFromRequest(httpServletRequest)));
			jobPosting.setCity(this.cityService.findByCityName(jobPostingRequest.getCityName()));
			jobPosting.setApplicationDeadline(jobPostingRequest.getApplicationDeadline());
			jobPosting.setJobTitle(this.jobTitleService.findByTitle(jobPostingRequest.getJobTitleName()));
			jobPosting.setCriteria(jobPostingRequest.getCriteria());
			jobPosting.setCvMandatory(jobPostingRequest.isCvMandatory());
			this.jobPostingRepository.save(jobPosting);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Kayıt işlemi başarılı");
	}

	@Override
	public DataResult<Page<JobPostingListRequest>> getAll(Pageable pageable) {
		Page<JobPosting> jobPostings = this.jobPostingRepository.findAll(pageable);
		if (jobPostings.getContent().size() < 1) {
			return new ErrorDataResult<Page<JobPostingListRequest>>("İş ilanı bulunamadı!");
		}
		Page<JobPostingListRequest> jobPostingListRequests = new PageImpl<JobPostingListRequest>(jobPostings.stream()
				.map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(), item.isCvMandatory(),
						item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
						item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
						item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
						item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),
						item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
								.collect(Collectors.toList()),
						item.getPostApplications().stream()
								.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
								.collect(Collectors.toList()))

				).collect(Collectors.toList()), pageable, jobPostings.getTotalElements());

		return new SuccessDataResult<Page<JobPostingListRequest>>(jobPostingListRequests);

	}

	@Override
	public Result activePassive(Long jobPostingId) {
		JobPosting jobPosting = null;
		try {
			jobPosting = this.jobPostingRepository.findById(jobPostingId).get();
			if (jobPosting.isActive()) {
				jobPosting.setActive(false);
				this.jobPostingRepository.save(jobPosting);
				return new SuccessResult("İş ilanı pasifleştirildi!");
			}

			jobPosting.setActive(true);
			this.jobPostingRepository.save(jobPosting);

		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("İş ilanı aktifleştirildi!");
	}


	@Override
	public DataResult<Page<JobPostingListRequest>> getCandidateJobPosting(Long id, Pageable pageable) {
		Page<JobPosting> pageJobPosting = null;
		Page<JobPostingListRequest> pageJobPostingListRequest = null;
		try {
			pageJobPosting = this.jobPostingRepository.getCandidateJobPosting(id, pageable);
			pageJobPostingListRequest = new PageImpl<JobPostingListRequest>(
					pageJobPosting.stream().map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(),
							item.isCvMandatory(), item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
							item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
							item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
							item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),
							item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
									.collect(Collectors.toList()),
							item.getPostApplications().stream()
									.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, pageJobPosting.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<JobPostingListRequest>>("Listeleme sırasıda bir hata oluştu!");
		}

		return new SuccessDataResult<Page<JobPostingListRequest>>(pageJobPostingListRequest);
	}
	@Override
	public DataResult<Page<JobPostingListRequest>> getEmployerJobPosting(Long id, Pageable pageable) {
		Page<JobPosting> pageJobPosting = null;
		Page<JobPostingListRequest> pageJobPostingListRequest = null;
		try {
			pageJobPosting = this.jobPostingRepository.getEmployerJobPosting(id, pageable);
			pageJobPostingListRequest = new PageImpl<JobPostingListRequest>(
					pageJobPosting.stream().map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(),
							item.isCvMandatory(), item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
							item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
							item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
							item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),item.getDeleted(),
							item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
									.collect(Collectors.toList()),
							item.getPostApplications().stream()
									.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, pageJobPosting.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<JobPostingListRequest>>("Listeleme sırasıda bir hata oluştu!");
		}

		return new SuccessDataResult<Page<JobPostingListRequest>>(pageJobPostingListRequest);
	}


	@Override
	public DataResult<Page<JobPostingListRequest>> findActiveOrPassiveJobPosting(Long id, boolean status,
			Pageable pageable) {
		Page<JobPosting> pageJobPosting = null;
		Page<JobPostingListRequest> pageJobPostingListRequest = null;
		try {
			pageJobPosting = this.jobPostingRepository.findActiveOrPassiveJobPosting(id, status, pageable);
			pageJobPostingListRequest = new PageImpl<JobPostingListRequest>(
					pageJobPosting.stream().map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(),
							item.isCvMandatory(), item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
							item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
							item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
							item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),
							item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
									.collect(Collectors.toList()),
							item.getPostApplications().stream()
									.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, pageJobPosting.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<JobPostingListRequest>>("Listeleme sırasıda bir hata oluştu!");
		}

		return new SuccessDataResult<Page<JobPostingListRequest>>(pageJobPostingListRequest);
	}

	@Override
	public Result update(JobPostingUpdateRequest jobPostingRequest, HttpServletRequest httpServletRequest) {
		JobPosting jobPosting = null;
		try {
			jobPosting = this.jobPostingRepository.findById(jobPostingRequest.getId()).orElseThrow();
			jobPosting.setCity(this.cityService.findByCityName(jobPostingRequest.getCityName()));
			jobPosting.setJobTitle(this.jobTitleService.findByTitle(jobPostingRequest.getJobTitleName()));
			jobPosting.setMaxSalary(jobPostingRequest.getMaxSalary());
			jobPosting.setMinSalary(jobPostingRequest.getMinSalary());
			jobPosting.setPostSummary(jobPostingRequest.getPostSummary());
			jobPosting.setApplicationDeadline(jobPostingRequest.getApplicationDeadline());
			jobPosting.setNumberOfAvailablePosition(jobPostingRequest.getNumberOfAvailablePosition());
			jobPosting.setCriteria(jobPostingRequest.getCriteria());
			jobPosting.setCvMandatory(jobPostingRequest.isCvMandatory());
			this.jobPostingRepository.save(jobPosting);

		} catch (Exception e) {
			return new ErrorResult("Hata :" + e.getMessage());
		}
		return new SuccessResult("Güncellendi");
	}

	@Override
	public Result deleteJobPosting(Long id) {
		JobPosting jobPosting = null;
		try {
			jobPosting = this.jobPostingRepository.findById(id).orElseThrow();
			jobPosting.setDeleted('1');
			this.jobPostingRepository.save(jobPosting);
		} catch (Exception e) {
			return new ErrorResult("Hata :" + e.getMessage());
		}
		return new SuccessResult("Silindi");
	}

	@Override
	public JobPosting findById(Long id) {

		return this.jobPostingRepository.findById(id).orElseThrow();
	}

	@Override
	public void saveForOtherServices(JobPosting jobPosting) {
		this.jobPostingRepository.save(jobPosting);

	}

	@Override
	public DataResult<Page<JobPostingListRequest>> getInterestingJobPostings(Pageable pageable, Long id) {

		Page<JobPosting> pageJobPosting = null;
		Page<JobPostingListRequest> pageJobPostingListRequest = null;

		Date date = new Date();
		try {
			final Candidate candidate = this.candidateService.findByIdForSevices(id);

			if (candidate.getApplications().size() > 0) {
				List<Long> candidateAppIds = candidate.getApplications().stream().map(JobPosting::getId)
						.collect(Collectors.toList());
				List<String> titles = candidate.getApplications().stream().map(item -> item.getJobTitle().getTitle())
						.collect(Collectors.toList());
				List<String> cities = candidate.getApplications().stream().map(item -> item.getCity().getCityName())
						.collect(Collectors.toList());
				pageJobPosting = this.jobPostingRepository.findByJobTitle_TitleIn(titles, date, candidateAppIds,
						pageable);
				if ((pageJobPosting.getContent().size() < 1)) {

					pageJobPosting = this.jobPostingRepository.findByCities_CityName(cities, date, candidateAppIds,
							pageable);
				}
				if ((pageJobPosting.getContent().size() < 1)) {

					pageJobPosting = this.jobPostingRepository.findByLikeCount(date, candidateAppIds, pageable);
				}

			} else {

				return new ErrorDataResult<>("İlgi çekici ilan bulunamadı");
			}

			pageJobPostingListRequest = new PageImpl<JobPostingListRequest>(pageJobPosting.stream()
					.map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(), item.isCvMandatory(),
							item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
							item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
							item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
							item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),
							item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
									.collect(Collectors.toList()),
							item.getPostApplications().stream()
									.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
									.collect(Collectors.toList())))

					.collect(Collectors.toList()), pageable, pageJobPosting.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<JobPostingListRequest>>(
					"Listeleme sırasıda bir hata oluştu! : " + e.getMessage());
		}

		return new SuccessDataResult<Page<JobPostingListRequest>>(pageJobPostingListRequest);
	}

	@Override
	public DataResult<Page<JobPostingListRequest>> getFollowedCompanysJobPosting(Long userIdFromRequest,
			Pageable pageable) {

		Page<JobPosting> pageJobPosting = null;
		Page<JobPostingListRequest> pageJobPostingListRequest = null;

		try {
			Candidate candidate = this.candidateService.findByIdForSevices(userIdFromRequest);
			List<Long> employerIds = candidate.getFollowings().stream().map(Employer::getId)
					.collect(Collectors.toList());
			pageJobPosting = this.jobPostingRepository.getFollowedCompanysJobPosting(employerIds, pageable);

			pageJobPostingListRequest = new PageImpl<JobPostingListRequest>(pageJobPosting.stream()
					.map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(), item.isCvMandatory(),
							item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
							item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
							item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
							item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),
							item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
									.collect(Collectors.toList()),
							item.getPostApplications().stream()
									.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
									.collect(Collectors.toList())))

					.collect(Collectors.toList()), pageable, pageJobPosting.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<JobPostingListRequest>>("Hata :" + e.getMessage());
		}
		return new SuccessDataResult<Page<JobPostingListRequest>>(pageJobPostingListRequest);
	}

	@Override
	public DataResult<Page<JobPostingListRequest>> searchJobPosting(ListRequest listRequest) {
		JobPostingSpecification jobPostingSpecification = null;
		Page<JobPosting> jobPostingPage = null;
		Page<JobPostingListRequest> jobPostingListRequestPage = null;

		try {
			jobPostingSpecification = new JobPostingSpecification(listRequest.getSearch());
			jobPostingPage = this.jobPostingRepository.findAll(jobPostingSpecification, listRequest.getPageable());
	

			jobPostingListRequestPage = new PageImpl<JobPostingListRequest>(jobPostingPage.stream()
					.map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(), item.isCvMandatory(),
							item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
							item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
							item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
							item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),
							item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
									.collect(Collectors.toList()),
							item.getPostApplications().stream()
									.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
									.collect(Collectors.toList())))

					.collect(Collectors.toList()), listRequest.getPageable(), jobPostingPage.getTotalElements());

		} catch (Exception e) {
			return new SuccessDataResult<Page<JobPostingListRequest>>("Hata :" + e.getMessage());
		}
		return new SuccessDataResult<Page<JobPostingListRequest>>(jobPostingListRequestPage);
	}

	@Override
	public DataResult<Page<JobPostingListRequest>> getByJobPosition(String searchingWord, Pageable pageable) {
		Page<JobPosting> jobPostingPage = null;
		Page<JobPostingListRequest> jobPostingListRequestPage = null;
		
		try {
			jobPostingPage = this.jobPostingRepository.findByJobTitle_TitleLikeIgnoreCase(searchingWord, pageable);
		
			jobPostingListRequestPage = new PageImpl<JobPostingListRequest>(jobPostingPage.stream()
					.map(item -> new JobPostingListRequest(item.getId(), item.getCriteria(), item.isCvMandatory(),
							item.getMaxSalary(), item.getMinSalary(), item.getPostSummary(),
							item.getNumberOfAvailablePosition(), item.getApplicationDeadline(), item.getCreatedDate(),
							item.getEmployer().getCompanyName(), item.getEmployer().getProfilePhoto(),
							item.getJobTitle().getTitle(), item.getCity().getCityName(), item.getEmployer().getId(),
							item.getLikes().stream().map(like -> this.modelMapper.map(like, LikesRequest.class))
									.collect(Collectors.toList()),
							item.getPostApplications().stream()
									.map(postApp -> this.modelMapper.map(postApp, PostApplicationsRequest.class))
									.collect(Collectors.toList())))

					.collect(Collectors.toList()),pageable, jobPostingPage.getTotalElements());

		} catch (Exception e) {
			return new SuccessDataResult<Page<JobPostingListRequest>>("Hata :" + e.getMessage());
		}
		return new SuccessDataResult<Page<JobPostingListRequest>>(jobPostingListRequestPage);
	}

	@Override
	public int getAllCount() {
		return this.jobPostingRepository.getAllCount();
	}




}
