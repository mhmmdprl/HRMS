package com.kodlamaio.hrms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.model.CandidateGetConnecRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidate;
import com.kodlamaio.hrms.model.CandidateGetRequest;
import com.kodlamaio.hrms.model.CandidateUpdateRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface CandidateService  extends BaseService<Candidate>{

	public boolean existsEmployeeByEmail(String email);

	public DataResult<List<Candidate>> findAll();
	
	public Candidate findByIdForSevices(Long id);
	
	public DataResult<CandidateGetRequest> getById(Long id);

	public Result updateCandidate(@Valid CandidateUpdateRequest candidateUpdateRequest,
			HttpServletRequest httpServletRequest);

	public DataResult<Page<CandidateGetRequest>> searchCandidate(ListRequest listRequest);

	public DataResult<Page<CandidateGetRequest>> getByNameOrLastName(String searchingWord,Pageable pageable);

	public Result addImage(MultipartFile file, HttpServletRequest httpServletRequest);

	public Result connect(Long id, HttpServletRequest httpServletRequest);

	public DataResult<Page<CandidateGetConnecRequest>> getConnections(Long id, Pageable pageable,HttpServletRequest httpServletRequest);

	public DataResult<Page<CandidateGetConnecRequest>> getCandidateConnections(Long id, Pageable pageable);
	
	public Result followOrUnFollowEmployer(Long id, HttpServletRequest httpServletRequest);

	public Result likeOrDislikeJobPosting(Long id, HttpServletRequest httpServletRequest);

	public Result applyJobPost(Long id, Long userIdFromRequest);

	public DataResult<Page<MainFeaturesOfTheCandidate>> getInterestingCandidate(Long userIdFromRequest,
			Pageable pageable);

	public DataResult<Page<MainFeaturesOfTheCandidate>> getMyCommonConnect(Long id, Long userIdFromRequest,
			Pageable pageable);

	public Result likeOrDislikePost(Long id, HttpServletRequest httpServletRequest);

	public int getAllCount();

	public Page<Candidate> getAllCandidate(Pageable pageable);

	public Page<Candidate> findByEmailLike(String email, Pageable pageable);

	public DataResult<Page<CandidateGetConnecRequest>> getEmployerFollowers(Long id, Pageable pageable);


	
	
}
