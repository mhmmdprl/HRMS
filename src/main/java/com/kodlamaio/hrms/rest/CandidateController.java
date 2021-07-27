package com.kodlamaio.hrms.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.model.CandidateGetConnecRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidate;
import com.kodlamaio.hrms.model.CandidateGetRequest;
import com.kodlamaio.hrms.model.CandidateSaveRequest;
import com.kodlamaio.hrms.model.CandidateUpdateRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/candidate")
@CrossOrigin
public class CandidateController {

	@Autowired
	private CandidateService candidateService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/save")
	public Result saveCandidate(@Valid @RequestBody CandidateSaveRequest candidateRequest) {

		Candidate candidate = new Candidate();
		candidate.setName(candidateRequest.getName());
		candidate.setLastName(candidateRequest.getLastName());
		candidate.setEmail(candidateRequest.getEmail());
		candidate.setPassword(this.bCryptPasswordEncoder.encode(candidateRequest.getPassword()));
		candidate.setBirtOfDate(candidateRequest.getBirtOfDate());
		candidate.setIdentityNumber(candidateRequest.getIdentityNumber());
		candidate.setGender(candidateRequest.getGender());

		return this.candidateService.save(candidate);
	}

	@GetMapping("/getAll")
	public DataResult<List<Candidate>> getAll() {

		return this.candidateService.findAll();
	}

	@GetMapping("getCandidate")
	public DataResult<CandidateGetRequest> getCandidate(HttpServletRequest httpServletRequest) {

		return this.candidateService.getById(this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}

	@PutMapping("updateCandidate")
	public Result updateCandidate(@Valid @RequestBody CandidateUpdateRequest candidateUpdateRequest,
			HttpServletRequest httpServletRequest) {
		return this.candidateService.updateCandidate(candidateUpdateRequest, httpServletRequest);
	}

	@PostMapping("/search")
	public DataResult<Page<CandidateGetRequest>> searchCandidate(@RequestBody ListRequest listRequest,
			@RequestParam int page, @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size);
		listRequest.setPageable(pageable);
		return this.candidateService.searchCandidate(listRequest);
	}

	@GetMapping("/getbynameorlastname")
	public DataResult<Page<CandidateGetRequest>> getByNameOrLastName(@RequestParam String searchingWord,
			@RequestParam int page, @RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
		return this.candidateService.getByNameOrLastName(searchingWord, pageable);
	}

	@GetMapping("getCandidateById")
	public DataResult<CandidateGetRequest> getById(Long id) {

		return this.candidateService.getById(id);
	}

	@PostMapping("/addImage")
	public Result addImage(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

		return this.candidateService.addImage(file, httpServletRequest);
	}

	@GetMapping("/getConnections")
	public DataResult<Page<CandidateGetConnecRequest>> getConnections(@RequestParam Long id, @RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size);
		return this.candidateService.getConnections(id, pageable, httpServletRequest);

	}

	@GetMapping("getInterestingCandidate")
	public DataResult<Page<MainFeaturesOfTheCandidate>> getInterestingCandidate(@RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size);
		return this.candidateService
				.getInterestingCandidate(this.tokenProvider.getUserIdFromRequest(httpServletRequest), pageable);

	}

	@PostMapping("/follow")
	public Result followOrUnFollowEmployer(@RequestParam Long id, HttpServletRequest httpServletRequest) {

		return this.candidateService.followOrUnFollowEmployer(id, httpServletRequest);
	}

	@PostMapping("/likeOrDislikeJobPosting")
	public Result likeOrDislikeJobPosting(@RequestParam Long id, HttpServletRequest httpServletRequest) {

		return this.candidateService.likeOrDislikeJobPosting(id, httpServletRequest);
	}

	@PostMapping("/likeOrDislikePost")
	public Result likeOrDislikePost(@RequestParam Long id, HttpServletRequest httpServletRequest) {

		return this.candidateService.likeOrDislikePost(id, httpServletRequest);
	}

	@PostMapping("applyJobPost")
	public Result applyJobPost(@RequestParam Long id, HttpServletRequest httpServletRequest) {

		return this.candidateService.applyJobPost(id, this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}

	@GetMapping("getMyCommonConnect")
	public DataResult<Page<MainFeaturesOfTheCandidate>> getMyCommonConnect(@RequestParam int page,
			@RequestParam int size, @RequestParam Long id, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
		return this.candidateService.getMyCommonConnect(id, this.tokenProvider.getUserIdFromRequest(httpServletRequest),
				pageable);
	}

	@PutMapping("connect")
	public Result connect(@RequestParam Long id, HttpServletRequest httpServletRequest) {

		return this.candidateService.connect(id, httpServletRequest);
	}

}
