package com.kodlamaio.hrms.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.entities.Verification;
import com.kodlamaio.hrms.model.EmployerRequest;
import com.kodlamaio.hrms.repository.EmployerRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.service.EmailService;
import com.kodlamaio.hrms.service.EmployerService;
import com.kodlamaio.hrms.service.VerificationService;

@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private VerificationService verificationService;

	@Autowired
	private EmailService emailService;

	@Override
	public DataResult<EmployerRequest> save(EmployerRequest employerRequest) {
		Employer employer = null;
		String[] emailParse = null;
		String webParse = null;
		String token = null;
		Verification verification = null;
		String url = "http://localhost:8080/activation?token=";
		try {
			if (this.employerRepository.existsByEmail(employerRequest.getEmail())) {

				return new ErrorDataResult<>("Email kullanımda");
			}
			employer = new Employer();
			if (!employerRequest.getWebAdress().startsWith("www.")) {
				return new ErrorDataResult<EmployerRequest>("Geçersiz web sitesi!");
			}
			emailParse = employerRequest.getEmail().split("@");
			webParse = employerRequest.getWebAdress().replace("www.", "");
			if (!webParse.equals(emailParse[1])) {
				return new ErrorDataResult<>("Eposta ile Web sitesi aynı domaine sahip olmalıdır.");
			}
			token = UUID.randomUUID().toString();
			employer.setEmail(employerRequest.getEmail());
			employer.setPassword(employerRequest.getPassword());
			employer.setCompanyName(employerRequest.getCompanyName());
			employer.setPhoneNumber(employerRequest.getPhoneNumber());
			employer.setWebAdress(employerRequest.getWebAdress());
			this.employerRepository.save(employer);

			verification = new Verification();
			verification.setVerificationCode(token);
			verification.setUserId(employer.getId());
			this.verificationService.save(verification);
			this.emailService.sendSimpleMessage(employerRequest.getEmail(), "Aktivasyon Mail", url + token);
		} catch (Exception e) {
			return new ErrorDataResult<>(e.getMessage());
		}

		return new SuccessDataResult<>("Kayıt işlemi tamamlandı");
	}

	@Override
	public DataResult<List<Employer>> getAll() {
		
		return new SuccessDataResult<List<Employer>>(this.employerRepository.findAll());
	}

}
