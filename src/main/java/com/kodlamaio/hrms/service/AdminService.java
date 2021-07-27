package com.kodlamaio.hrms.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

public interface AdminService {

	DataResult<CountRequest> getAllCount();

	DataResult<Page<MainFeaturesOfTheCandidateForAdmin>> getAllCandidate(Pageable pageable);

	DataResult<Page<MainFeaturesOfTheCandidateForAdmin>> findByEmailLike(String email, Pageable pageable);

	Result lockAccount(Long id);

	Result deleteAccount(Long id);

	DataResult<Page<ChannelGetRequest>> getCandidateChannels(Long id, Pageable pageable);

	DataResult<Page<MessageGetRequest>> getCandidateMessagesOfChannel(String uuid, Long id, Pageable pageable);

	DataResult<MainFeaturesOfTheCandidateForAdmin> getCandidateForAdmin(Long id);

	DataResult<Page<PostForAdminGetRequest>> getCandidatePosts(Long id, Pageable pageable);

	Result deleteOrLoadPost(Long id);

	DataResult<Page<CandidateGetConnecRequest>> getCandidateConnections(Long id, Pageable pageable,
			HttpServletRequest httpServletRequest);

	DataResult<Page<MainFeaturesOfTheEmployer>> getCandidateFollows(Long id, Pageable pageable,
			HttpServletRequest httpServletRequest);

	DataResult<Page<CommentForAdminRequest>> getCandidateComments(Long id, Pageable pageable);

	Result deleteOrLoadComment(Long id);

	DataResult<Page<JobPostingListRequest>> getCandidateJobPosting(Long id, Pageable pageable);

	DataResult<CvGetRequest> getCandidateCv(Long id);

	DataResult<Page<MainFeaturesOfTheEmployer>> getAllEmployer(Pageable pageable);

	DataResult<Page<MainFeaturesOfTheEmployer>> findByEmailLikeEmployer(String email, Pageable pageable);

	DataResult<MainFeaturesOfTheEmployer> getEmployerForAdmin(Long id);

	DataResult<Page<JobPostingListRequest>> getEmployerJobPosting(Long id, Pageable pageable);

	Result deleteOrAddJobPosting(Long id);

	DataResult<Page<CandidateGetConnecRequest>> getEmployerFollowers(Long id, Pageable pageable,
			HttpServletRequest httpServletRequest);

	DataResult<Page<MainFeaturesOfTheEmployer>> getPassiveEmployer(Pageable pageable);

	Result comfirmEmployer(String email);

	DataResult<AdminRequest> getAdmin(Long userIdFromRequest);

}
