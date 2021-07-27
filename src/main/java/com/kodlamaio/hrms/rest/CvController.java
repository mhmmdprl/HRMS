package com.kodlamaio.hrms.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.kodlamaio.hrms.service.CvService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/cv")
@CrossOrigin
public class CvController {

	@Autowired
	private CvService cvService;
	
	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping(path = "/save")
	public Result save(@RequestBody CvSaveRequest cvSaveRequest,HttpServletRequest request) {
 
		return this.cvService.save(cvSaveRequest,request);
	}

	@GetMapping("/getALL")
	public DataResult<List<CvGetRequest>> getAll() {

		return this.cvService.getAll();
	}

	@PostMapping("/addImage")
	public Result addImage(@RequestParam("file") MultipartFile file, @RequestParam Long id) {

		return this.cvService.addImage(file, id);
	}

	
	@GetMapping("/getCv")
	public DataResult<CvGetRequest> getCv(HttpServletRequest httpServletRequest){
		
		return this.cvService.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
	@GetMapping("/getCandidateCv")
	public DataResult<CvGetRequest> getCandidateCv(@RequestParam Long id){
		
		return this.cvService.findById(id);
	}
	
	@PutMapping("/schoolupdate")
	public Result updateSchool(@RequestParam Long cvId,@RequestBody List<SchoolRequest>  schoolRequest) {
		
		return this.cvService.updateSchool(cvId,schoolRequest);
	}
	@PutMapping("/experienceupdate")
	public Result updateExperience(@RequestParam Long cvId,@RequestBody List<WorkExperienceRequest>  experiences) {
		
		
		return this.cvService.updateExperience(cvId,experiences);
	}
	@PutMapping("/updatelang")
	public Result updateLanguages(@RequestParam Long cvId,@RequestBody List<LanguagesRequest>  langs) {
		
		
		return this.cvService.updateLanguages(cvId,langs);
	}
	@PutMapping("/updateproglang")
	public Result updateProgLangs(@RequestParam Long cvId,@RequestBody List<ProgrammingLanguageRequest>  progLangs) {
		
		
		return this.cvService.updateProgLanguages(cvId,progLangs);
	}
	@PutMapping("/updateability")
	public Result updateAbilties(@RequestParam Long cvId,@RequestBody List<AbilityRequest>  abilities) {
		
		return this.cvService.updateAbilities(cvId,abilities);
	}

}
