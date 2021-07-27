package com.kodlamaio.hrms.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.AdminRequest;
import com.kodlamaio.hrms.model.CandidateGetConnecRequest;
import com.kodlamaio.hrms.model.ChannelGetRequest;
import com.kodlamaio.hrms.model.CommentForAdminRequest;
import com.kodlamaio.hrms.model.CountRequest;
import com.kodlamaio.hrms.model.CvGetRequest;
import com.kodlamaio.hrms.model.JobPostingListRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidateForAdmin;
import com.kodlamaio.hrms.model.MainFeaturesOfTheEmployer;
import com.kodlamaio.hrms.model.MessageGetRequest;
import com.kodlamaio.hrms.model.PostForAdminGetRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.AdminService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/admin")
@CrossOrigin
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private TokenProvider tokenProvider;

	@GetMapping("getAllCount")
	public DataResult<CountRequest> getAllCount(HttpServletRequest httpServletRequest) {
		return this.adminService.getAllCount();
	}

	@GetMapping("getAllEmployer")
	public DataResult<Page<MainFeaturesOfTheEmployer>> getAllEmployer(@RequestParam int page, @RequestParam int size,
			HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));
		return this.adminService.getAllEmployer(pageable);
	}

	@GetMapping("findByEmailLikeEmployer")
	public DataResult<Page<MainFeaturesOfTheEmployer>> findByEmailLikeEmployer(@RequestParam String email,
			@RequestParam int page, @RequestParam int size, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));
		return this.adminService.findByEmailLikeEmployer(email, pageable);
	}

	@GetMapping("getPassiveEmployer")
	public DataResult<Page<MainFeaturesOfTheEmployer>> getPassiveEmployer(@RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));
		return this.adminService.getPassiveEmployer(pageable);
	}

	@GetMapping("getAllCandidate")
	public DataResult<Page<MainFeaturesOfTheCandidateForAdmin>> getAllCandidate(@RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));
		return this.adminService.getAllCandidate(pageable);
	}

	@GetMapping("findByEmailLike")
	public DataResult<Page<MainFeaturesOfTheCandidateForAdmin>> findByEmailLike(@RequestParam String email,
			@RequestParam int page, @RequestParam int size, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));
		return this.adminService.findByEmailLike(email, pageable);
	}

	@PutMapping("lockAccount")
	public Result lockAccount(@RequestParam Long id, HttpServletRequest httpServletRequest) {

		return this.adminService.lockAccount(id);
	}

	@PutMapping("deleteAccount")
	public Result deleteAccount(@RequestParam Long id, HttpServletRequest httpServletRequest) {

		return this.adminService.deleteAccount(id);
	}

	@GetMapping("/getCandidateChannels")
	public DataResult<Page<ChannelGetRequest>> getCandidateChannels(@RequestParam Long id, @RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("updatedDate").descending());
		return this.adminService.getCandidateChannels(id, pageable);
	}

	@GetMapping("getCandidateMessagesOfChannel")
	public DataResult<Page<MessageGetRequest>> getCandidateMessagesOfChannel(@RequestParam String uuid,
			@RequestParam Long id, @RequestParam int page, @RequestParam int size,
			HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
		return this.adminService.getCandidateMessagesOfChannel(uuid, id, pageable);

	}

	@GetMapping("getCandidateForAdmin")
	public DataResult<MainFeaturesOfTheCandidateForAdmin> getCandidateForAdmin(@RequestParam Long id,
			HttpServletRequest httpServletRequest) {

		return this.adminService.getCandidateForAdmin(id);
	}

	@GetMapping("getEmployerForAdmin")
	public DataResult<MainFeaturesOfTheEmployer> getEmployerForAdmin(@RequestParam Long id,
			HttpServletRequest httpServletRequest) {
		return this.adminService.getEmployerForAdmin(id);
	}

	@GetMapping("/getCandidatePostsForAdmin")
	public DataResult<Page<PostForAdminGetRequest>> getCandidatePosts(@RequestParam int page, @RequestParam int size,
			@RequestParam Long id, HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.adminService.getCandidatePosts(id, pageable);
	}

	@PutMapping("deleteOrAddJobPosting")
	public Result deleteOrAddJobPosting(@RequestParam Long id, HttpServletRequest httpServletRequest) {
		return this.adminService.deleteOrAddJobPosting(id);
	}

	@PutMapping("deleteOrLoadPost")
	public Result deleteOrLoadPost(@RequestParam Long id, HttpServletRequest httpServletRequest) {
		return this.adminService.deleteOrLoadPost(id);
	}

	@PutMapping("deleteOrLoadComment")
	public Result deleteOrLoadComment(@RequestParam Long id, HttpServletRequest httpServletRequest) {
		return this.adminService.deleteOrLoadComment(id);
	}

	@GetMapping("/getCandidateConnections")
	public DataResult<Page<CandidateGetConnecRequest>> getCandidateConnections(@RequestParam Long id,
			@RequestParam int page, @RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.adminService.getCandidateConnections(id, pageable, httpServletRequest);

	}

	@GetMapping("/getEmployerFollowers")
	public DataResult<Page<CandidateGetConnecRequest>> getEmployerFollowers(@RequestParam Long id,
			@RequestParam int page, @RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.adminService.getEmployerFollowers(id, pageable, httpServletRequest);

	}

	@GetMapping("/getCandidateFollows")
	public DataResult<Page<MainFeaturesOfTheEmployer>> getCandidateFollows(@RequestParam Long id,
			@RequestParam int page, @RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.adminService.getCandidateFollows(id, pageable, httpServletRequest);

	}

	@GetMapping("/getCandidateComments")
	public DataResult<Page<CommentForAdminRequest>> getCandidateComments(@RequestParam Long id, @RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.adminService.getCandidateComments(id, pageable);

	}

	@GetMapping("/getCandidateJobPosting")
	public DataResult<Page<JobPostingListRequest>> getCandidateJobPosting(@RequestParam Long id, @RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.adminService.getCandidateJobPosting(id, pageable);

	}

	@GetMapping("/getEmployerJobPosting")
	public DataResult<Page<JobPostingListRequest>> getEmployerJobPosting(@RequestParam Long id, @RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.adminService.getEmployerJobPosting(id, pageable);

	}

	@GetMapping("/getCandidateCv")
	public DataResult<CvGetRequest> getCandidateCv(@RequestParam Long id) {

		return this.adminService.getCandidateCv(id);
	}
	@PutMapping("/comfirm")
	public Result comfirmEmployer(@RequestParam String email) {
		return this.adminService.comfirmEmployer(email);
	}

	@GetMapping("getAdmin")
	public DataResult<AdminRequest> getAdmin(HttpServletRequest httpServletRequest){
	
		return this.adminService.getAdmin(this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
	}
