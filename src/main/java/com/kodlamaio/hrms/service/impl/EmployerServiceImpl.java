package com.kodlamaio.hrms.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.entities.Verification;
import com.kodlamaio.hrms.model.EmployerGetRequest;
import com.kodlamaio.hrms.model.EmployerSaveRequest;
import com.kodlamaio.hrms.model.EmployerUpdateRequest;
import com.kodlamaio.hrms.model.FollowersCandidateRequest;
import com.kodlamaio.hrms.model.ListRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheEmployer;
import com.kodlamaio.hrms.repository.EmployerRepository;
import com.kodlamaio.hrms.repository.specification.EmployerSpecification;
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
import com.kodlamaio.hrms.service.RoleService;
import com.kodlamaio.hrms.service.VerificationService;
import com.kodlamaio.hrms.util.TokenProvider;

@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private VerificationService verificationService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private BCryptPasswordEncoder bCryptPassEncoder;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private ImageService imageService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SimpMessagingTemplate simpMessaginTemplate;

	@Override
	@Transactional
	public Result save(EmployerSaveRequest employerRequest) {
		Employer employer = null;
		String[] emailParse = null;
		String webParse = null;
		String token = null;
		Verification verification = null;
		String url = "http://localhost:3000/auth/activation/";
		try {

			if (this.employerRepository.existsByEmail(employerRequest.getEmail())) {
				employer = this.employerRepository.findByEmail(employerRequest.getEmail());
				if (employer.isAcctive()) {
					return new ErrorResult("Email kullanımda");
				}

				verification = this.verificationService.findByUserId(employer.getId());
				if (this.verificationService.validateToken(verification)) {
					verification.setDeleted('1');
					this.verificationService.save(verification);
					Verification newVerification = new Verification();
					token = UUID.randomUUID().toString();
					newVerification.setVerificationCode(token);
					newVerification.setExpiryDate(new Date(System.currentTimeMillis() + Verification.EXPIRATION));
					newVerification.setUserId(employer.getId());
					this.emailService.sendSimpleMessage(employerRequest.getEmail(), "Aktivasyon Mail", url + token);
					return new SuccessResult(
							"Email adresinize yeni aktivasyon kodu gönderildi.Süresi bitmeden onaylayınız");
				}
				token = verification.getVerificationCode();
				this.emailService.sendSimpleMessage(employerRequest.getEmail(), "Aktivasyon Mail", url + token);
				return new SuccessResult("Email adresinize token tekrar gönderildi");

			}
			employer = new Employer();
			if (!employerRequest.getWebAddress().startsWith("www.")) {
				return new ErrorResult("Geçersiz web sitesi!");
			}
			emailParse = employerRequest.getEmail().split("@");
			webParse = employerRequest.getWebAddress().replace("www.", "");
			if (!webParse.equals(emailParse[1])) {
				return new ErrorResult("Eposta ile Web sitesi aynı domaine sahip olmalıdır.");
			}
			token = UUID.randomUUID().toString();
			employer.setEmail(employerRequest.getEmail());
			employer.setPassword(this.bCryptPassEncoder.encode(employerRequest.getPassword()));
			employer.setCompanyName(employerRequest.getCompanyName());
			employer.setPhoneNumber(employerRequest.getPhoneNumber());
			employer.setWebAddress(employerRequest.getWebAddress());
			employer.setAboutCompany(employerRequest.getAboutCompany());
			employer.setRoles(Arrays.asList(this.roleService.findById(2l)));
			this.employerRepository.save(employer);

			verification = new Verification();
			verification.setVerificationCode(token);
			verification.setUserId(employer.getId());
			verification.setExpiryDate(new Date(System.currentTimeMillis() + Verification.EXPIRATION));
			this.verificationService.save(verification);
			this.emailService.sendSimpleMessage(employerRequest.getEmail(), "Aktivasyon Mail", url + token);
		} catch (Exception e) {
			return new ErrorResult(e.getMessage());
		}
		this.simpMessaginTemplate.convertAndSend("/queue/admin-indexpage",true);
		return new SuccessResult("Kayıt işlemi tamamlandı");
	}

	@Override
	public DataResult<List<Employer>> getAll() {

		return new SuccessDataResult<List<Employer>>(this.employerRepository.findAll());
	}

	@Override
	public DataResult<EmployerGetRequest> findById(Long employerId) {
		Employer employer = null;
		EmployerGetRequest employerRequest = null;
		try {
			employer = this.employerRepository.findById(employerId).get();
			employerRequest = this.modelMapper.map(employer, EmployerGetRequest.class);

		} catch (Exception e) {
			return new ErrorDataResult<EmployerGetRequest>("Listeleme işleminde bir hata oluştu");
		}
		return new SuccessDataResult<EmployerGetRequest>(employerRequest);
	}

	@Override
	public Employer getById(Long employerId) {

		return this.employerRepository.findById(employerId).orElseThrow();
	}

	@Override
	@Transactional
	public Result updateEmployer(EmployerUpdateRequest employerUpdateRequest, HttpServletRequest httpServletRequest) {
		Employer employer = null;
		String[] emailParse = null;
		String webParse = "";
		try {
			if (!employerUpdateRequest.getWebAddress().startsWith("www.")) {
				return new ErrorResult("Geçersiz web sitesi!");
			}

			employer = this.employerRepository.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest))
					.orElseThrow();
			emailParse = employer.getEmail().split("@");
			webParse = employerUpdateRequest.getWebAddress().replace("www.", "");
			if (!webParse.equals(emailParse[1])) {
				return new ErrorResult("Eposta ile Web sitesi aynı domaine sahip olmalıdır.");
			}
			employer.setPhoneNumber(employerUpdateRequest.getPhoneNumber());
			employer.setCompanyName(employerUpdateRequest.getCompanyName());
			employer.setWebAddress(employerUpdateRequest.getWebAddress());
			this.employerRepository.save(employer);
		} catch (Exception e) {
			return new ErrorResult("Güncelleme sırasında bir hata oluştu");
		}
		return new SuccessResult("Güncellendi");
	}

	@Override
	public Result addImage(MultipartFile file, HttpServletRequest httpServletRequest) {
		Employer employer = null;
		try {
			employer = this.employerRepository.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest))
					.orElseThrow();
			DataResult<Map<String, String>> result = this.imageService.uploadImage(file);
			employer.setProfilePhoto(result.getData().get("url"));
			this.employerRepository.save(employer);

		} catch (Exception e) {
			return new ErrorResult("Fotoğraf yüklenirken bir sorun oluştu!");
		}

		return new SuccessResult("Fotoğraf yüklendi");
	}

	@Override
	@Transactional
	public DataResult<Page<EmployerGetRequest>> getByCompanyNameLike(String searhingWord, Pageable pageable) {
		Page<Employer> pageEmployer = null;
		Page<EmployerGetRequest> pageEmployerGetRequest = null;
		try {
			pageEmployer = this.employerRepository.findByCompanyNameLike(searhingWord, pageable);
			pageEmployerGetRequest = new PageImpl<EmployerGetRequest>(
					pageEmployer.stream().map(item -> new EmployerGetRequest(item.getId(), item.getEmail(),
							item.getCompanyName(), item.getWebAddress(), item.getPhoneNumber(), item.getProfilePhoto(),
							item.getAboutCompany(),
							item.getFollowers().stream()
									.map(follower -> this.modelMapper.map(follower, FollowersCandidateRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, pageEmployer.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<>("Hata :" + e.getMessage());
		}
		return new SuccessDataResult<Page<EmployerGetRequest>>(pageEmployerGetRequest);
	}

	@Override
	public void saveForOtherService(Employer employer) {

		this.employerRepository.save(employer);

	}

	@Override
	@Transactional
	public Result unFollow(Long candidateId, HttpServletRequest httpServletRequest) {

		try {
			Candidate candidate = this.candidateService.findByIdForSevices(candidateId);
			Employer employer = this.employerRepository
					.findById(this.tokenProvider.getUserIdFromRequest(httpServletRequest)).orElseThrow();
			candidate.getFollowings().removeIf(obj -> obj.getId() == employer.getId());
			employer.getFollowers().removeIf(obj -> obj.getId() == candidate.getId());
			this.employerRepository.save(employer);
			this.candidateService.save(candidate);

		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Takipten çıkartıldı");
	}

	@Override
	public DataResult<Page<EmployerGetRequest>> getInterestingEmployer(Long userIdFromRequest, Pageable pageable) {
		Page<Employer> employerPage = null;
		Page<EmployerGetRequest> employerPageRequest = null;
		Candidate candidate = null;
		List<Long> ids = null;
		try {

			candidate = this.candidateService.findByIdForSevices(userIdFromRequest);
			ids = candidate.getFollowings().stream().map(Employer::getId).collect(Collectors.toList());
			employerPage = this.employerRepository.findInterestinFollowingEmployer(ids, pageable);
//			if (employerPage.getTotalElements() < 1) {
//				employerPage = this.employerRepository.findInterestinPostCountEmployer(pageable);
//			}

			if (employerPage.getTotalElements() < 1) {
				employerPage = this.employerRepository.findInterestinRandEmployer(ids, pageable);
			}
			employerPageRequest = new PageImpl<EmployerGetRequest>(
					employerPage.stream().map(item -> new EmployerGetRequest(item.getId(), item.getEmail(),
							item.getCompanyName(), item.getWebAddress(), item.getPhoneNumber(), item.getProfilePhoto(),
							item.getAboutCompany(),
							item.getFollowers().stream()
									.map(follow -> this.modelMapper.map(follow, FollowersCandidateRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, employerPage.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<EmployerGetRequest>>("Hata :" + e.getMessage());
		}
		return new SuccessDataResult<Page<EmployerGetRequest>>(employerPageRequest);
	}

	@Override
	public DataResult<Page<EmployerGetRequest>> searchJobEmployer(ListRequest listRequest) {
		Page<Employer> employerPage = null;
		Page<EmployerGetRequest> employerPageRequest = null;
		EmployerSpecification employerSpecification = null;
		try {
			employerSpecification = new EmployerSpecification(listRequest.getSearch());
			employerPage = this.employerRepository.findAll(employerSpecification, listRequest.getPageable());

			employerPageRequest = new PageImpl<EmployerGetRequest>(
					employerPage.stream().map(item -> new EmployerGetRequest(item.getId(), item.getEmail(),
							item.getCompanyName(), item.getWebAddress(), item.getPhoneNumber(), item.getProfilePhoto(),
							item.getAboutCompany(),
							item.getFollowers().stream()
									.map(follow -> this.modelMapper.map(follow, FollowersCandidateRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					listRequest.getPageable(), employerPage.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<EmployerGetRequest>>("Hata :" + e.getMessage());
		}
		return new SuccessDataResult<Page<EmployerGetRequest>>(employerPageRequest);
	}

	@Override
	public int getAllCount() {
		return this.employerRepository.getAllCount();
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheEmployer>> getCandidateFollows(Long id, Pageable pageable) {
		Page<Employer> pageEmployer = null;
		Page<MainFeaturesOfTheEmployer> pageMainFeaturesOfTheEmployer = null;
		try {
		
			pageEmployer = this.employerRepository.getCandidateFollows(id, pageable);
			pageMainFeaturesOfTheEmployer = new PageImpl<MainFeaturesOfTheEmployer>(
					pageEmployer.stream().map(item -> this.modelMapper.map(item, MainFeaturesOfTheEmployer.class))
							.collect(Collectors.toList()),pageable,pageEmployer.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheEmployer>>("Hata : "+e.getMessage());
		}
		return new SuccessDataResult<Page<MainFeaturesOfTheEmployer>>(pageMainFeaturesOfTheEmployer);
	}

	@Override
	public DataResult<Page<MainFeaturesOfTheEmployer>> getPassiveEmployer(Pageable pageable) {
		Page<Employer> pageEmployer = null;
		Page<MainFeaturesOfTheEmployer> pageMainFeaturesOfTheEmployer = null;
		try {
		
			pageEmployer = this.employerRepository.getPassiveEmployer( pageable);
			pageMainFeaturesOfTheEmployer = new PageImpl<MainFeaturesOfTheEmployer>(
					pageEmployer.stream().map(item -> this.modelMapper.map(item, MainFeaturesOfTheEmployer.class))
							.collect(Collectors.toList()),pageable,pageEmployer.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<MainFeaturesOfTheEmployer>>("Hata : "+e.getMessage());
		}
		return new SuccessDataResult<Page<MainFeaturesOfTheEmployer>>(pageMainFeaturesOfTheEmployer);
	}
	@Override
	public Page<Employer> getAllEmployer(Pageable pageable) {
		return this.employerRepository.findAll(pageable);
	}

	@Override
	public Page<Employer> findByEmailLikeEmployer(String email, Pageable pageable) {
		
		return this.employerRepository.findByEmailLikeEmployer(email,pageable);
	}

	@Override
	public Employer getEmployerForAdmin(Long id) {
		return this.employerRepository.findById(id).orElseThrow();
	}

	@Override
	public boolean existsEmployerById(Long id) {
	
		return this.employerRepository.existsById(id);
	}

	@Override
	public Employer findEmployerOtherService(Long id) {
		return this.employerRepository.findById(id).orElseThrow();
	}

	@Override
	public boolean existsByEmail(String email) {
		return this.employerRepository.existsByEmail(email);
	}

	@Override
	public Employer findByEmail(String email) {
		return this.employerRepository.findByEmail(email);
	}


}
