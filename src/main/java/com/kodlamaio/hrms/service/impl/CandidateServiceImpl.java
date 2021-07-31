package com.kodlamaio.hrms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Verification;
import com.kodlamaio.hrms.enums.NatificationType;
import com.kodlamaio.hrms.model.AplicationsRequest;
import com.kodlamaio.hrms.model.CandidateGetConnecRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidate;
import com.kodlamaio.hrms.model.CandidateGetRequest;
import com.kodlamaio.hrms.model.CandidateUpdateRequest;
import com.kodlamaio.hrms.model.FollowingEmployerRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.entities.JobPosting;
import com.kodlamaio.hrms.entities.Natification;
import com.kodlamaio.hrms.entities.Post;
import com.kodlamaio.hrms.repository.CandidateRepository;
import com.kodlamaio.hrms.repository.specification.CandidateSpecification;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.EmailService;
import com.kodlamaio.hrms.service.EmployerService;
import com.kodlamaio.hrms.service.ImageService;
import com.kodlamaio.hrms.service.JobPostingService;
import com.kodlamaio.hrms.service.NatifacationService;
import com.kodlamaio.hrms.service.PostService;
import com.kodlamaio.hrms.service.RoleService;
import com.kodlamaio.hrms.service.VerificationService;
import com.kodlamaio.hrms.util.TokenProvider;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class CandidateServiceImpl extends BaseResultService<Candidate> implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private KPSPublicSoapProxy kpsPublicSoapProxy;
	@Autowired
	private VerificationService verificationService;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private EmailService emailService;
	@Autowired
	private ImageService imageService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmployerService employerService;

	@Autowired
	private JobPostingService jobPostingService;

	@Autowired
	private PostService postService;

	@Autowired
	private NatifacationService natificationService;
	
	@Autowired
	private RoleService  roleService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemp;

	@Override
	@Transactional
	public Result save(Candidate requestCandidate) {
		Candidate candidate = null;
		Verification verification = null;
		String token = null;
		String url = "http://mpiral.com/auth/activation/";
		try {
			if (this.existsEmployeeByEmail(requestCandidate.getEmail())) {
				candidate = this.candidateRepository.findByEmail(requestCandidate.getEmail());
				if (candidate.isAcctive()) {
					return new ErrorResult("Bu email kullanımda");
				}
				verification = this.verificationService.findByUserId(candidate.getId());
				if (this.verificationService.validateToken(verification)) {
					verification.setDeleted('1');
					this.verificationService.save(verification);
					Verification newVerification = new Verification();
					token = UUID.randomUUID().toString();
					newVerification.setVerificationCode(token);
					newVerification.setExpiryDate(new Date(System.currentTimeMillis() + Verification.EXPIRATION));
					newVerification.setUserId(candidate.getId());
					this.emailService.sendSimpleMessage(requestCandidate.getEmail(), "Aktivasyon Mail", url + token);
					return new SuccessResult(
							"Email adresinize yeni aktivasyon kodu gönderildi.Süresi bitmeden onaylayınız");
				}
				token = verification.getVerificationCode();
				this.emailService.sendSimpleMessage(requestCandidate.getEmail(), "Aktivasyon Mail", url + token);
				return new SuccessResult("Email adresinize token tekrar gönderildi");

			}
			if (this.candidateRepository.existsByIdentityNumber(requestCandidate.getIdentityNumber())) {

				return new ErrorResult("Tc no kullanımda");
			}
			if (!this.kpsPublicSoapProxy.TCKimlikNoDogrula(requestCandidate.getIdentityNumber(),
					requestCandidate.getName().toUpperCase(), requestCandidate.getLastName().toUpperCase(),
					requestCandidate.getBirtOfDate().getYear())) {
				return new ErrorResult("Tc vatandaş doğrulaması yapılamadı.");
			}
			token = UUID.randomUUID().toString();
			verification = new Verification();
			requestCandidate.setRoles(Arrays.asList(this.roleService.findByCode("CANDIDATE")));
			candidate = this.candidateRepository.save(requestCandidate);
			verification.setUserId(candidate.getId());
			verification.setVerificationCode(token);
			verification.setExpiryDate(new Date(System.currentTimeMillis() + Verification.EXPIRATION));
			this.verificationService.save(verification);
			this.emailService.sendSimpleMessage(requestCandidate.getEmail(), "Aktivasyon Mail", url + token);

		} catch (Exception e) {
			return new ErrorResult(e.getMessage());
		}
		this.simpMessagingTemp.convertAndSend("/queue/admin-indexpage",true);
		return new SuccessResult(
				"Kaydınzı tamamlamak için Email adresinize gönderdiğimiz aktivasyon linkine tıklayınız!");
	}

	@Override
	public boolean existsEmployeeByEmail(String email) {

		return this.candidateRepository.existsByEmail(email);
	}

	@Override
	public DataResult<List<Candidate>> findAll() {
		return new SuccessDataResult<List<Candidate>>(this.candidateRepository.findAll());
	}

	@Override
	public Result updateCandidate(@Valid CandidateUpdateRequest candidateUpdateRequest,
			HttpServletRequest httpServletRequest) {

		Candidate candidate = null;
		try {
			candidate = this.candidateRepository.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest))
					.get();
			candidate.setName(candidateUpdateRequest.getName());
			candidate.setLastName(candidateUpdateRequest.getLastName());
			candidate.setBirtOfDate(candidateUpdateRequest.getBirtOfDate());

			this.candidateRepository.save(candidate);

		} catch (Exception e) {
			return new ErrorResult("Güncelleme işlemi başarısız oldu!");
		}
		return new SuccessResult("Bilgiler güncellendi");
	}

	@Override
	public DataResult<Page<CandidateGetRequest>> searchCandidate(ListRequest listRequest) {
		CandidateSpecification candidateSpecification = null;
		Page<Candidate> candidates = null;
		Page<CandidateGetRequest> candidateGetRequest = null;
		try {
			candidateSpecification = new CandidateSpecification(listRequest.getSearch());
			candidates = this.candidateRepository.findAll(candidateSpecification, listRequest.getPageable());
			if (candidates.getContent().size() < 1) {
				return new ErrorDataResult<Page<CandidateGetRequest>>("Kişi bulunamadı!");
			}
			candidateGetRequest = new PageImpl<CandidateGetRequest>(candidates.stream()
					.map(item -> new CandidateGetRequest(item.getId(), item.getName(), item.getLastName(),
							item.getBirtOfDate(), item.getEmail(), item.getGender(), item.getProfilePhoto(),
							item.getMyConnections().stream()
									.map(connect -> this.modelMapper.map(connect, CandidateGetConnecRequest.class))
									.collect(Collectors.toList()),
							item.getFollowings().stream()
									.map(followingEmployer -> this.modelMapper.map(followingEmployer,
											FollowingEmployerRequest.class))
									.collect(Collectors.toList()),
							item.getApplications().stream()
									.map(app -> this.modelMapper.map(app, AplicationsRequest.class))
									.collect(Collectors.toList()),
							item.getCreatedDate())

					).collect(Collectors.toList()), listRequest.getPageable(), candidates.getTotalElements());

		} catch (Exception e) {
			new ErrorDataResult<CandidateGetRequest>("Bir hata oluştu");
		}

		return new SuccessDataResult<Page<CandidateGetRequest>>(candidateGetRequest);

	}

	@Override
	public DataResult<Page<CandidateGetRequest>> getByNameOrLastName(String searchingWord, Pageable pageable) {
		Page<Candidate> candidates = null;
		Page<CandidateGetRequest> candidateGetRequest = null;
		try {
			candidates = this.candidateRepository.findByNameLike(searchingWord, pageable);
			candidateGetRequest = new PageImpl<CandidateGetRequest>(candidates.stream()
					.map(item -> new CandidateGetRequest(item.getId(), item.getName(), item.getLastName(),
							item.getBirtOfDate(), item.getEmail(), item.getGender(), item.getProfilePhoto(),
							item.getMyConnections().stream()
									.map(connect -> this.modelMapper.map(connect, CandidateGetConnecRequest.class))
									.collect(Collectors.toList()),
							item.getFollowings().stream()
									.map(followingEmployer -> this.modelMapper.map(followingEmployer,
											FollowingEmployerRequest.class))
									.collect(Collectors.toList()),
							item.getApplications().stream()
									.map(app -> this.modelMapper.map(app, AplicationsRequest.class))
									.collect(Collectors.toList()),
							item.getCreatedDate())

					).collect(Collectors.toList()), pageable, candidates.getTotalElements());

		} catch (Exception e) {
			new ErrorDataResult<CandidateGetRequest>("Bir hata oluştu");
		}
		return new SuccessDataResult<Page<CandidateGetRequest>>(candidateGetRequest);
	}

	@Override
	public Result addImage(MultipartFile file, HttpServletRequest httpServletRequest) {
		Candidate candidate = null;
		try {
			candidate = this.candidateRepository.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest))
					.get();
			DataResult<Map<String, String>> result = this.imageService.uploadImage(file);
			candidate.setProfilePhoto(result.getData().get("url"));
			this.candidateRepository.save(candidate);

		} catch (Exception e) {
			return new ErrorResult("Fotoğraf yüklenirken bir sorun oluştu!");
		}

		return new SuccessResult("Fotoğraf yüklendi");
	}

	@Override
	public Result connect(Long id, HttpServletRequest httpServletRequest) {
		String message = "";
		Natification natification = null;
		try {
			if (id == this.tokenProvider.getUserIdFromRequest(httpServletRequest)) {
				return new ErrorResult();
			}
			final Candidate requestCandidate = this.candidateRepository
					.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest)).get();
			final Candidate responseCandidate = this.candidateRepository.findById(id).get();
			if (requestCandidate.getMyConnections().removeIf(obj -> (obj.getId() == responseCandidate.getId()))) {
				responseCandidate.getMyConnections().removeIf(obj -> (obj.getId() == requestCandidate.getId()));
				message = "unconnect";
				this.simpMessagingTemp.convertAndSend(
						"/queue/status-" + this.tokenProvider.getUserIdFromRequest(httpServletRequest), false);
			} else {
				requestCandidate.getMyConnections().add(responseCandidate);
				responseCandidate.getMyConnections().add(requestCandidate);
				message = "connect";
				natification = new Natification();
				natification.setToWho(responseCandidate);
				natification.setNatificationType(NatificationType.Connect);
				natification.setWho(requestCandidate);
				this.simpMessagingTemp.convertAndSend("/queue/navNatification-" + responseCandidate.getId(), true);
				this.natificationService.save(natification);
				this.simpMessagingTemp.convertAndSend(
						"/queue/status-" + this.tokenProvider.getUserIdFromRequest(httpServletRequest), true);
			}
			this.candidateRepository.save(requestCandidate);
			this.candidateRepository.save(responseCandidate);
		} catch (Exception e) {
			return new ErrorResult("HATA : " + e.getMessage());
		}

		return new SuccessResult(message);
	}

	@Override
	public DataResult<CandidateGetRequest> getById(Long id) {
		Candidate candidate = null;
		CandidateGetRequest candidateGetRequest = null;

		try {
			candidate = this.candidateRepository.findById(id).get();
			candidateGetRequest = new CandidateGetRequest();
			candidateGetRequest.setId(candidate.getId());
			candidateGetRequest.setBirtOfDate(candidate.getBirtOfDate());
			candidateGetRequest.setProfilePhoto(candidate.getProfilePhoto());
			candidateGetRequest.setEmail(candidate.getEmail());
			candidateGetRequest.setGender(candidate.getGender());
			candidateGetRequest.setIdentityNumber(candidate.getIdentityNumber());
			candidateGetRequest.setLastName(candidate.getLastName());
			candidateGetRequest.setName(candidate.getName());
			candidateGetRequest.setMyConnections(candidate.getMyConnections().stream()
					.map(item -> this.modelMapper.map(item, CandidateGetConnecRequest.class))
					.collect(Collectors.toList()));
			candidateGetRequest.setFollowings(candidate.getFollowings().stream()
					.map(item -> this.modelMapper.map(item, FollowingEmployerRequest.class))
					.collect(Collectors.toList()));
			candidateGetRequest.setApplications(candidate.getApplications().stream()
					.filter(item -> item.getDeleted() == '0')
					.map(item -> this.modelMapper.map(item, AplicationsRequest.class)).collect(Collectors.toList()));
			if (candidate.getCv() == null) {
				candidateGetRequest.setHasCv(false);
			} else {
				candidateGetRequest.setHasCv(true);
			}
		} catch (Exception e) {
			return new ErrorDataResult<CandidateGetRequest>("Bir hata oluştu :" + e.getMessage());
		}

		return new SuccessDataResult<CandidateGetRequest>(candidateGetRequest);
	}

	@Override
	public Candidate findByIdForSevices(Long id) {
		return this.candidateRepository.findById(id).get();
	}

	@Override
	public DataResult<Page<CandidateGetConnecRequest>> getConnections(Long id, Pageable pageable,
			HttpServletRequest httpServletRequest) {
		Candidate candidate = null;
		Page<CandidateGetConnecRequest> pageConnections = null;
		Comparator<? super Candidate> comparator;
		Candidate user = null;

		try {
			user = this.candidateRepository.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest))
					.get();
			final List<Long> ids = new ArrayList<Long>();
			user.getMyConnections().forEach(item -> {
				ids.add(item.getId());
			});

			comparator = Comparator.comparing(item -> ids.contains(item.getId()));

			candidate = this.candidateRepository.findById(id).get();
			pageConnections = new PageImpl<CandidateGetConnecRequest>(candidate.getMyConnections().stream()
					.sorted(comparator.reversed())
					.map(item -> new CandidateGetConnecRequest(item.getId(), item.getName(), item.getLastName(),
							item.getEmail(), item.getProfilePhoto(), item.getGender()))
					.collect(Collectors.toList()), pageable, candidate.getMyConnections().size());

		} catch (Exception e) {
			return new ErrorDataResult<Page<CandidateGetConnecRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<CandidateGetConnecRequest>>(pageConnections);
	}

	@Override
	public Result followOrUnFollowEmployer(Long id, HttpServletRequest httpServletRequest) {
		String message = "";
		try {
			if (id == this.tokenProvider.getUserIdFromRequest(httpServletRequest)) {
				return new ErrorResult();
			}
			final Employer employer = this.employerService.getById(id);
			final Candidate candidate = this.candidateRepository
					.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest)).get();
			if (candidate.getFollowings().removeIf(item -> (item.getId() == employer.getId()))) {
				employer.getFollowers().removeIf(item -> (item.getId() == candidate.getId()));
				message = "unfollow";
			} else {
				candidate.getFollowings().add(employer);
				employer.getFollowers().add(candidate);
				message = "follow";
			}

			this.employerService.saveForOtherService(employer);
			this.candidateRepository.save(candidate);
		} catch (Exception e) {
			message = "Hata :" + e.getMessage();
			return new ErrorResult(message);
		}
		return new SuccessResult(message);
	}

	@Override
	public Result likeOrDislikeJobPosting(Long id, HttpServletRequest httpServletRequest) {

		String message = "";
		try {
			JobPosting jobPosting = this.jobPostingService.findById(id);
			Candidate candidate = this.candidateRepository
					.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest)).get();
			if (candidate.getWhatILikes().removeIf(item -> item.getId() == jobPosting.getId())) {
				jobPosting.getLikes().removeIf(item -> item.getId() == candidate.getId());
				message = "dislike";
			} else {
				candidate.getWhatILikes().add(jobPosting);
				jobPosting.getLikes().add(candidate);
				message = "like";
			}

			this.candidateRepository.save(candidate);
			this.jobPostingService.saveForOtherServices(jobPosting);
		} catch (Exception e) {
			return new ErrorResult("Hata :" + e.getMessage());
		}
		return new SuccessResult(message);
	}

	@Override
	public Result likeOrDislikePost(Long id, HttpServletRequest httpServletRequest) {
		String message = "";
		Natification natification = null;
		try {
			Post post = this.postService.findByIdForOtherService(id);
			Candidate candidate = this.candidateRepository
					.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest)).get();
			if (candidate.getWhatILikesPost().removeIf(item -> item.getId() == post.getId())) {
				post.getLikes().removeIf(item -> item.getId() == candidate.getId());
				message = "dislike";
				candidate.getMyConnections().forEach(item -> {
					System.out.println(item.getId());
					this.simpMessagingTemp.convertAndSend("/queue/homePage-" + item.getId(), true);
				});
				this.simpMessagingTemp.convertAndSend("/queue/status-" + post.getCandidate().getId(), true);

			} else {
				candidate.getWhatILikesPost().add(post);
				post.getLikes().add(candidate);
				message = "like";
				if (post.getCandidate().getId() != this.tokenProvider.getUserIdFromRequest(httpServletRequest)) {
					natification = new Natification();
					natification.setToWho(post.getCandidate());
					natification.setNatificationType(NatificationType.Like);
					natification.setWho(candidate);
					this.simpMessagingTemp.convertAndSend("/queue/navNatification-" + post.getCandidate().getId(),
							true);
					this.natificationService.save(natification);
				}
				this.simpMessagingTemp.convertAndSend("/queue/status-" + post.getCandidate().getId(), true);
				candidate.getMyConnections().forEach(item -> {
					this.simpMessagingTemp.convertAndSend("/queue/homePage-" + item.getId(), true);
				});
			}

			this.candidateRepository.save(candidate);
			this.postService.saveForOtherService(post);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult(message);
	}

	@Override
	public Result applyJobPost(Long id, Long userIdFromRequest) {
		String message = "";
		try {
			JobPosting jobPosting = this.jobPostingService.findById(id);
			Candidate candidate = this.candidateRepository.findById(userIdFromRequest).get();
			if (jobPosting.getPostApplications().contains(candidate)) {
				message = "Daha önce başvurunuz bulunmaktadır";
				return new ErrorResult(message);
			} else if (jobPosting.getPostApplications().size() >= jobPosting.getNumberOfAvailablePosition()) {
				message = "Kabul edilen başvuru sayısına ulaşıldı!";
				return new ErrorResult(message);
			} else if (new Date().after(jobPosting.getApplicationDeadline())) {
				message = "Son başvuru tarihi geçmiş.";
				return new ErrorResult(message);

			} else if (candidate.getCv() == null) {
				if (jobPosting.isCvMandatory()) {
					message = "Bu ilanda cv zorunlulugu vardır";
					return new ErrorResult(message);
				} else {
					candidate.getApplications().add(jobPosting);
					jobPosting.getPostApplications().add(candidate);
					this.candidateRepository.save(candidate);
					this.jobPostingService.saveForOtherServices(jobPosting);
				}

			} else {
				candidate.getApplications().add(jobPosting);
				jobPosting.getPostApplications().add(candidate);

				this.candidateRepository.save(candidate);
				this.jobPostingService.saveForOtherServices(jobPosting);
			}

			message = "Başvuru yapıldı";

		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult(message);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheCandidate>> getInterestingCandidate(Long userIdFromRequest,
			Pageable pageable) {
		Candidate candidate = null;
		Page<Candidate> pageCandidate = null;
		Page<MainFeaturesOfTheCandidate> pageCandidateGetInterestingRequest = null;
		try {
			candidate = this.candidateRepository.findById(userIdFromRequest).get();
			pageCandidate = this.candidateRepository.getCommonConnects(userIdFromRequest,
					candidate.getMyConnections().stream().map(Candidate::getId).collect(Collectors.toList()),
					candidate.getMyConnections().stream().map(Candidate::getId).collect(Collectors.toList()), pageable);

			pageCandidateGetInterestingRequest = new PageImpl<MainFeaturesOfTheCandidate>(pageCandidate.stream()
					.map(item -> new MainFeaturesOfTheCandidate(item.getId(), item.getName(), item.getLastName(),
							item.getProfilePhoto(), item.getGender()))
					.collect(Collectors.toList()), pageable, pageCandidate.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheCandidate>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<MainFeaturesOfTheCandidate>>(pageCandidateGetInterestingRequest);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheCandidate>> getMyCommonConnect(Long id, Long userIdFromRequest,
			Pageable pageable) {
		Page<Candidate> pageCandidate = null;
		Page<MainFeaturesOfTheCandidate> pageCandidateGetInterestingRequest = null;
		try {
			pageCandidate = this.candidateRepository.getMyCommonConnect(id, userIdFromRequest, pageable);
			pageCandidateGetInterestingRequest = new PageImpl<MainFeaturesOfTheCandidate>(pageCandidate.stream()
					.map(item -> new MainFeaturesOfTheCandidate(item.getId(), item.getName(), item.getLastName(),
							item.getProfilePhoto(), item.getGender()))
					.collect(Collectors.toList()), pageable, pageCandidate.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheCandidate>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<MainFeaturesOfTheCandidate>>(pageCandidateGetInterestingRequest);
	}

	@Override
	public int getAllCount() {

		return this.candidateRepository.getAllCount();
	}

	@Override
	public Page<Candidate> getAllCandidate(Pageable pageable) {

		return this.candidateRepository.getAllCandidate(pageable);
	}

	@Override
	public Page<Candidate> findByEmailLike(String email, Pageable pageable) {

		return this.candidateRepository.findByEmailLike(email, pageable);
	}

	@Override
	public DataResult<Page<CandidateGetConnecRequest>> getCandidateConnections(Long id, Pageable pageable) {
		Candidate candidate = null;
		Page<CandidateGetConnecRequest> pageConnections = null;
		try {
			candidate = this.candidateRepository.findById(id).get();
			pageConnections = new PageImpl<CandidateGetConnecRequest>(candidate.getMyConnections().stream()
					.map(item -> new CandidateGetConnecRequest(item.getId(), item.getName(), item.getLastName(),
							item.getEmail(), item.getProfilePhoto(), item.getGender()))
					.collect(Collectors.toList()), pageable, candidate.getMyConnections().size());

		} catch (Exception e) {
			return new ErrorDataResult<Page<CandidateGetConnecRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<CandidateGetConnecRequest>>(pageConnections);
	}

	@Override
	public DataResult<Page<CandidateGetConnecRequest>> getEmployerFollowers(Long id, Pageable pageable) {
		Page<Candidate> pageCandidate=null; 
		Page<CandidateGetConnecRequest> pageConnections = null;
		
		try {
			pageCandidate=this.candidateRepository.getEmployerFollowers(id,pageable);
			
			pageConnections = new PageImpl<CandidateGetConnecRequest>(pageCandidate.stream()
					.map(item -> new CandidateGetConnecRequest(item.getId(), item.getName(), item.getLastName(),
							item.getEmail(), item.getProfilePhoto(), item.getGender()))
					.collect(Collectors.toList()), pageable, pageCandidate.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<CandidateGetConnecRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<CandidateGetConnecRequest>>(pageConnections);
	}


}
