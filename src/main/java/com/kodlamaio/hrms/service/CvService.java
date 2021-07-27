package com.kodlamaio.hrms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.model.AbilityRequest;
import com.kodlamaio.hrms.model.CvGetRequest;
import com.kodlamaio.hrms.model.CvSaveRequest;
import com.kodlamaio.hrms.model.LanguagesRequest;
import com.kodlamaio.hrms.model.ProgrammingLanguageRequest;
import com.kodlamaio.hrms.model.SchoolRequest;
import com.kodlamaio.hrms.model.WorkExperienceRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface CvService {

	Result save(CvSaveRequest cvSaveRequest,HttpServletRequest request);

	DataResult<List<CvGetRequest>> getAll();

	Result addImage(MultipartFile file, Long id);

	DataResult<CvGetRequest> findById(Long id);


	Result updateSchool(Long cvId, List<SchoolRequest> schoolRequest);

	Result updateExperience(Long cvId, List<WorkExperienceRequest> experiences);

	Result updateLanguages(Long cvId, List<LanguagesRequest> langs);

	Result updateProgLanguages(Long cvId, List<ProgrammingLanguageRequest> progLangs);

	Result updateAbilities(Long cvId, List<AbilityRequest> abilities);
	

}
