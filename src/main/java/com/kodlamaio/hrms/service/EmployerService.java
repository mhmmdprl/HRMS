package com.kodlamaio.hrms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.model.EmployerGetRequest;
import com.kodlamaio.hrms.model.EmployerSaveRequest;
import com.kodlamaio.hrms.model.EmployerUpdateRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheEmployer;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface EmployerService extends BaseService<EmployerSaveRequest> {

	DataResult<List<Employer>> getAll();

	DataResult<EmployerGetRequest> findById(Long employerId);

	Employer getById(Long employerId);

	Result updateEmployer(EmployerUpdateRequest employerUpdateRequest, HttpServletRequest httpServletRequest);

	Result addImage(MultipartFile file, HttpServletRequest httpServletRequest);

	DataResult<Page<EmployerGetRequest>> getByCompanyNameLike(String searhingWord,Pageable pageable);

	void saveForOtherService(Employer employer);

	Result unFollow(Long candidateId, HttpServletRequest httpServletRequest);

	DataResult<Page<EmployerGetRequest>> getInterestingEmployer(Long userIdFromRequest, Pageable pageable);

	DataResult<Page<EmployerGetRequest>> searchJobEmployer(ListRequest listRequest);

	int getAllCount();

	DataResult<Page<MainFeaturesOfTheEmployer>> getCandidateFollows(Long id, Pageable pageable);

	Page<Employer> getAllEmployer(Pageable pageable);

	Page<Employer> findByEmailLikeEmployer(String email, Pageable pageable);

	Employer getEmployerForAdmin(Long id);

	DataResult<Page<MainFeaturesOfTheEmployer>> getPassiveEmployer(Pageable pageable);

	boolean existsEmployerById(Long id);

	Employer findEmployerOtherService(Long id);

	boolean existsByEmail(String email);

	Employer findByEmail(String email);

	
	

}
