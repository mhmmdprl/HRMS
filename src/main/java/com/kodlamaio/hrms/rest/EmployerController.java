package com.kodlamaio.hrms.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.model.EmployerGetRequest;
import com.kodlamaio.hrms.model.EmployerSaveRequest;
import com.kodlamaio.hrms.model.EmployerUpdateRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.EmployerService;
import com.kodlamaio.hrms.util.TokenProvider;


@RestController
@RequestMapping("api/employer")
@CrossOrigin
public class EmployerController {

	@Autowired
	private EmployerService employerService;
	@Autowired
	private TokenProvider tokeProvider;
 	
	@PostMapping("/save")
	public Result saveEmployer(@Valid @RequestBody EmployerSaveRequest employerRequest) {
		return this.employerService.save(employerRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<Employer>> getAll(){
		
		return this.employerService.getAll();
	}
	@GetMapping("/getByCompanyNameLike")
	public DataResult<Page<EmployerGetRequest>> getByCompanyNameLike(@RequestParam String searhingWord,@RequestParam int page,@RequestParam int size){
		Pageable pageable=PageRequest.of(page,size);
		return this.employerService.getByCompanyNameLike(searhingWord,pageable);
	}
	
	@GetMapping("/getEmployer")
	public DataResult<EmployerGetRequest> getEmployer(HttpServletRequest httpServletRequest){
		
		return this.employerService.findById(this.tokeProvider.getUserIdFromRequest(httpServletRequest));
	}
	
	@GetMapping("/getEmployerById")
	public DataResult<EmployerGetRequest> getEmployerById(@RequestParam Long id){
		
		return this.employerService.findById(id);
	}
	@PutMapping("/updateEmployer")
	public Result updateEmployer(@Valid @RequestBody EmployerUpdateRequest employerUpdateRequest,HttpServletRequest httpServletRequest){
		
		return this.employerService.updateEmployer(employerUpdateRequest,httpServletRequest);
	}
	@PostMapping("/addImage")
	public Result addImage(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

		return this.employerService.addImage(file, httpServletRequest);
	}
	@PostMapping("/unFollowCandidate")
	public Result unFollowCandidate(@RequestParam Long candidateId,HttpServletRequest httpServletRequest) {
		
		return this.employerService.unFollow(candidateId,httpServletRequest);
	}
	
	@GetMapping("/getInterestingEmployer")
	public DataResult<Page<EmployerGetRequest>> getInterestingEmployer(@RequestParam int page,@RequestParam int size,HttpServletRequest httpServletRequest){
		
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC ,"createdDate"));
		
	return	this.employerService.getInterestingEmployer(this.tokeProvider.getUserIdFromRequest(httpServletRequest),pageable);
		
	}
	@PostMapping("/search")
	public DataResult<Page<EmployerGetRequest>> searchCandidate(@RequestBody ListRequest listRequest,
			@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		listRequest.setPageable(pageable);
		return this.employerService.searchJobEmployer(listRequest);
	}
}
