package com.kodlamaio.hrms.service.impl;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Comment;
import com.kodlamaio.hrms.entities.Employee;
import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.entities.JobPosting;
import com.kodlamaio.hrms.entities.Post;
import com.kodlamaio.hrms.entities.User;
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
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.AdminService;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.ChannelService;
import com.kodlamaio.hrms.service.CommentService;
import com.kodlamaio.hrms.service.CvService;
import com.kodlamaio.hrms.service.EmployeeService;
import com.kodlamaio.hrms.service.EmployerService;
import com.kodlamaio.hrms.service.JobPostingService;
import com.kodlamaio.hrms.service.MessageService;
import com.kodlamaio.hrms.service.PostService;
import com.kodlamaio.hrms.service.UserService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CandidateService candidateService;
	@Autowired
	private PostService postService;
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CvService cvService;
	@Autowired
	private UserService userService;

	@Autowired
	private EmployeeService employeeService;

	@Override
	public DataResult<CountRequest> getAllCount() {

		CountRequest countRequest = new CountRequest();
		try {
			countRequest.setUserCount(this.candidateService.getAllCount());
			countRequest.setJobPostingCount(this.jobPostingService.getAllCount());
			countRequest.setPostCount(this.postService.getAllCount());
			countRequest.setEmployerCount(this.employerService.getAllCount());

		} catch (Exception e) {
			return new ErrorDataResult<CountRequest>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<CountRequest>(countRequest);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheCandidateForAdmin>> getAllCandidate(Pageable pageable) {
		Page<MainFeaturesOfTheCandidateForAdmin> pageMainFeaturesCand = null;
		Page<Candidate> pageCandidate = null;
		try {
			pageCandidate = this.candidateService.getAllCandidate(pageable);

			pageMainFeaturesCand = new PageImpl<MainFeaturesOfTheCandidateForAdmin>(pageCandidate.stream()
					.map(item -> this.modelMapper.map(item, MainFeaturesOfTheCandidateForAdmin.class))
					.collect(Collectors.toList()), pageable, pageCandidate.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheCandidateForAdmin>>("Hata :" + e.getMessage());
		}

		return new SuccessDataResult<Page<MainFeaturesOfTheCandidateForAdmin>>(pageMainFeaturesCand);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheEmployer>> getAllEmployer(Pageable pageable) {
		Page<Employer> pageEmployer = null;
		Page<MainFeaturesOfTheEmployer> pageMainFeaturesOfTheEmployer = null;
		try {
			pageEmployer = this.employerService.getAllEmployer(pageable);

			pageMainFeaturesOfTheEmployer = new PageImpl<MainFeaturesOfTheEmployer>(
					pageEmployer.stream().map(item -> this.modelMapper.map(item, MainFeaturesOfTheEmployer.class))
							.collect(Collectors.toList()),
					pageable, pageEmployer.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheEmployer>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<MainFeaturesOfTheEmployer>>(pageMainFeaturesOfTheEmployer);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheEmployer>> findByEmailLikeEmployer(String email, Pageable pageable) {
		Page<Employer> pageEmployer = null;
		Page<MainFeaturesOfTheEmployer> pageMainFeaturesOfTheEmployer = null;
		try {
			pageEmployer = this.employerService.findByEmailLikeEmployer(email, pageable);

			pageMainFeaturesOfTheEmployer = new PageImpl<MainFeaturesOfTheEmployer>(
					pageEmployer.stream().map(item -> this.modelMapper.map(item, MainFeaturesOfTheEmployer.class))
							.collect(Collectors.toList()),
					pageable, pageEmployer.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheEmployer>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<MainFeaturesOfTheEmployer>>(pageMainFeaturesOfTheEmployer);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheCandidateForAdmin>> findByEmailLike(String email, Pageable pageable) {
		Page<MainFeaturesOfTheCandidateForAdmin> pageMainFeaturesCand = null;
		Page<Candidate> pageCandidate = null;
		try {
			pageCandidate = this.candidateService.findByEmailLike(email, pageable);

			pageMainFeaturesCand = new PageImpl<MainFeaturesOfTheCandidateForAdmin>(pageCandidate.stream()
					.map(item -> this.modelMapper.map(item, MainFeaturesOfTheCandidateForAdmin.class))
					.collect(Collectors.toList()), pageable, pageCandidate.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheCandidateForAdmin>>("Hata :" + e.getMessage());
		}

		return new SuccessDataResult<Page<MainFeaturesOfTheCandidateForAdmin>>(pageMainFeaturesCand);
	}

	@Override
	public DataResult<Page<ChannelGetRequest>> getCandidateChannels(Long id, Pageable pageable) {

		return this.channelService.getCandidateChannels(id, pageable);
	}

	@Override
	public DataResult<Page<MessageGetRequest>> getCandidateMessagesOfChannel(String uuid, Long id, Pageable pageable) {

		return this.messageService.getCandidateMessagesOfChannel(uuid, id, pageable);
	}

	@Override
	public DataResult<MainFeaturesOfTheEmployer> getEmployerForAdmin(Long id) {
		Employer employer = null;
		try {
			employer = this.employerService.getEmployerForAdmin(id);
		} catch (Exception e) {
			return new ErrorDataResult<MainFeaturesOfTheEmployer>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<MainFeaturesOfTheEmployer>(
				this.modelMapper.map(employer, MainFeaturesOfTheEmployer.class));
	}

	@Override
	public DataResult<MainFeaturesOfTheCandidateForAdmin> getCandidateForAdmin(Long id) {
		Candidate candidate = null;
		try {
			candidate = this.candidateService.findByIdForSevices(id);

		} catch (Exception e) {
			return new ErrorDataResult<MainFeaturesOfTheCandidateForAdmin>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<MainFeaturesOfTheCandidateForAdmin>(
				this.modelMapper.map(candidate, MainFeaturesOfTheCandidateForAdmin.class));
	}

	@Override
	public DataResult<Page<PostForAdminGetRequest>> getCandidatePosts(Long id, Pageable pageable) {
		return this.postService.getCandidatePostsForAdmin(id, pageable);
	}

	@Override
	public Result deleteOrLoadPost(Long id) {

		Post post = null;
		try {
			post = this.postService.findByIdForOtherServiceForAdmin(id);
			post.setDeleted(post.getDeleted() == '0' ? '1' : '0');
			this.postService.saveForOtherService(post);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Güncellendi");
	}

	@Override
	public Result deleteOrLoadComment(Long id) {
		Comment comment = null;

		try {
			comment = this.commentService.findByIdForAdmin(id);
			comment.setDeleted(comment.getDeleted() == '0' ? '1' : '0');
			this.commentService.saveForOtherService(comment);
		} catch (Exception e) {
			return new ErrorResult("Hata :" + e.getMessage());
		}
		return new SuccessResult("Güncellendi");
	}

	@Override
	public DataResult<Page<CandidateGetConnecRequest>> getCandidateConnections(Long id, Pageable pageable,
			HttpServletRequest httpServletRequest) {

		return this.candidateService.getCandidateConnections(id, pageable);
	}

	@Override
	public DataResult<Page<CandidateGetConnecRequest>> getEmployerFollowers(Long id, Pageable pageable,
			HttpServletRequest httpServletRequest) {

		return this.candidateService.getEmployerFollowers(id, pageable);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheEmployer>> getCandidateFollows(Long id, Pageable pageable,
			HttpServletRequest httpServletRequest) {
		return this.employerService.getCandidateFollows(id, pageable);
	}

	@Override
	public DataResult<Page<CommentForAdminRequest>> getCandidateComments(Long id, Pageable pageable) {
		return this.commentService.getCandidateComments(id, pageable);
	}

	@Override
	public DataResult<Page<JobPostingListRequest>> getCandidateJobPosting(Long id, Pageable pageable) {

		return this.jobPostingService.getCandidateJobPosting(id, pageable);
	}

	@Override
	public DataResult<Page<JobPostingListRequest>> getEmployerJobPosting(Long id, Pageable pageable) {
		return this.jobPostingService.getEmployerJobPosting(id, pageable);
	}

	@Override
	public DataResult<CvGetRequest> getCandidateCv(Long id) {
		return this.cvService.findById(id);
	}

	@Override
	public Result lockAccount(Long id) {
		User user = null;

		try {
			user = this.userService.findById(id);
			user.setLock(!user.isLock());
			this.userService.save(user);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Hesap Kitlendi");
	}

	@Override
	public Result deleteAccount(Long id) {
		User user = null;

		try {
			user = this.userService.findById(id);
			user.setDeleted(user.getDeleted() == '0' ? '1' : '0');
			this.userService.save(user);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Hesap Silindi");
	}

	@Override
	public Result deleteOrAddJobPosting(Long id) {

		JobPosting jobPosting = null;
		try {
			jobPosting = this.jobPostingService.findById(id);
			jobPosting.setDeleted(jobPosting.getDeleted() == '0' ? '1' : '0');
			this.jobPostingService.saveForOtherServices(jobPosting);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}

		return new SuccessResult("Güncellendi");
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheEmployer>> getPassiveEmployer(Pageable pageable) {

		return this.employerService.getPassiveEmployer(pageable);
	}

	@Override
	public Result comfirmEmployer(String email) {
		return this.employeeService.comfirmEmployer(email);
	}

	@Override
	public DataResult<AdminRequest> getAdmin(Long userIdFromRequest) {
		
		Employee employee=null;
		try {
		employee=this.employeeService.findByEmployeeId(userIdFromRequest);
		} catch (Exception e) {
			return new ErrorDataResult<AdminRequest>("Hata : "+e.getMessage());
		}
		return new SuccessDataResult<AdminRequest>(this.modelMapper.map(employee, AdminRequest.class));
	}

}
