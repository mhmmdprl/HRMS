package com.kodlamaio.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.CandidateDetail;
import com.kodlamaio.hrms.entities.Cv;
import com.kodlamaio.hrms.entities.Language;
import com.kodlamaio.hrms.entities.ProgrammingLanguage;
import com.kodlamaio.hrms.entities.School;
import com.kodlamaio.hrms.entities.WorkExperience;
import com.kodlamaio.hrms.enums.Status;
import com.kodlamaio.hrms.model.CandidateDetailRequest;
import com.kodlamaio.hrms.model.CvGetRequest;
import com.kodlamaio.hrms.model.CvSaveRequest;
import com.kodlamaio.hrms.model.LanguagesRequest;
import com.kodlamaio.hrms.model.ProgrammingLanguageRequest;
import com.kodlamaio.hrms.model.SchoolRequest;
import com.kodlamaio.hrms.model.WorkExperienceRequest;
import com.kodlamaio.hrms.repository.CvRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.AbilityService;
import com.kodlamaio.hrms.service.CandidateDetailService;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.CvService;
import com.kodlamaio.hrms.service.ImageService;

@Service
public class CvServiceImpl implements CvService {

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private CandidateDetailService candidateDetailService;
	
	@Autowired
	private CvRepository cvRepository;

	@Autowired
	private AbilityService abilityService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CandidateService candidateService;

	@Override
	public Result save(CvSaveRequest cvSaveRequest) {

		Cv cv = null;
	
		try {
			cv = new Cv();
			cv.setAbilities(cvSaveRequest.getAbilitiyIds().stream().map(item -> this.abilityService.findById(item))
					.collect(Collectors.toList()));
			CandidateDetail candidateDetail = new CandidateDetail();
			candidateDetail.setProgrammingLanguages(cvSaveRequest.getCandidateDetail().getProgrammingLanguages()
					.stream().map(item -> this.modelMapper.map(item, ProgrammingLanguage.class))
					.collect(Collectors.toList()));
			candidateDetail.setLanguages(cvSaveRequest.getCandidateDetail().getLanguages().stream()
					.map(item -> this.modelMapper.map(item, Language.class)).collect(Collectors.toList()));
			candidateDetail.setForeword(cvSaveRequest.getCandidateDetail().getForeword());
			candidateDetail.setGitHubAddress(cvSaveRequest.getCandidateDetail().getGitHubAddress());
			candidateDetail.setLinkedInAddress(cvSaveRequest.getCandidateDetail().getLinkedInAddress());
			cv.setCandidateDetail(candidateDetail);
			cv.setCvName(cvSaveRequest.getCvName());
			List<WorkExperience> experiences = cvSaveRequest.getExperiences().stream()
					.map(item -> this.modelMapper.map(item, WorkExperience.class)).collect(Collectors.toList());
			List<School> schools = cvSaveRequest.getSchoolRequests().stream()
					.map(item -> this.modelMapper.map(item, School.class)).collect(Collectors.toList());
			schools.forEach(item -> {

				if (item.getQuitDate() == null) {
					item.setStatus(Status.RESUM.getValue());
					item.setAvtice(true);
				} else {
					item.setStatus(Status.GRADUATED.getValue());
					item.setAvtice(false);
				}
			});

			experiences.forEach(item -> {

				if (item.getQuitDate() == null) {
					item.setStatus(Status.RESUM.getValue());
					item.setAvtice(true);
				} else {
					item.setStatus(Status.LEAVED.getValue());
					item.setAvtice(false);
				}
			});
			cv.setSchools(schools);
			cv.setExperiences(experiences);

			this.candidateDetailService.save(candidateDetail);
			this.cvRepository.save(cv);
			Candidate candidate = this.candidateService.findById(cvSaveRequest.getCandidateId());
			candidate.setCv(cv);
			this.candidateService.save(candidate);

		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}

		return new SuccessResult("Eklendi");
	}

	@Override
	public DataResult<List<CvGetRequest>> getAll() {
		List<Cv> cvs = null;
		List<CvGetRequest> cvGetRequests = new ArrayList<CvGetRequest>();
		try {
			cvs = this.cvRepository.findAll();

			cvs.forEach(item -> {
				CvGetRequest cvGetRequest = new CvGetRequest();
				cvGetRequest.setCvName(item.getCvName());
				cvGetRequest.setExperiences(item.getExperiences().stream()
						.map(experience -> this.modelMapper.map(experience, WorkExperienceRequest.class))
						.collect(Collectors.toList()));
				cvGetRequest.setSchoolRequests(item.getSchools().stream()
						.map(school -> this.modelMapper.map(school, SchoolRequest.class)).collect(Collectors.toList()));
				cvGetRequest.setCandidateDetail(
						this.modelMapper.map(item.getCandidateDetail(), CandidateDetailRequest.class));
				cvGetRequest.getCandidateDetail()
						.setProgrammingLanguages(item.getCandidateDetail().getProgrammingLanguages().stream()
								.map(plang -> this.modelMapper.map(plang, ProgrammingLanguageRequest.class))
								.collect(Collectors.toList()));
				cvGetRequest.getCandidateDetail().setLanguages(item.getCandidateDetail().getLanguages().stream()
						.map(lang -> this.modelMapper.map(lang, LanguagesRequest.class)).collect(Collectors.toList()));
				cvGetRequests.add(cvGetRequest);

			});

		} catch (Exception e) {
			return new ErrorDataResult<List<CvGetRequest>>("Hata : " + e.getMessage());
		}

		return new SuccessDataResult<List<CvGetRequest>>(cvGetRequests);
	}

	@Override
	public Result addImage(MultipartFile file, Long id) {
		Cv cv = this.cvRepository.findById(id).get();
		DataResult<Map<String, String>> result = this.imageService.uploadImage(file);
		cv.getCandidateDetail().setPhoto(result.getData().get("url"));
		this.cvRepository.save(cv);
		return new SuccessResult("Resim Eklendi");
	}

}
