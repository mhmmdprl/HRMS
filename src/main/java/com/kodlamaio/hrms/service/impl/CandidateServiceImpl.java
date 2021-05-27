package com.kodlamaio.hrms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Verification;
import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.repository.CandidateRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.EmailService;
import com.kodlamaio.hrms.service.VerificationService;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

//  String[] arr = str.split(" "); kullanılacak
@Service
public class CandidateServiceImpl extends BaseResultService<Candidate> implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private KPSPublicSoapProxy kpsPublicSoapProxy;
	@Autowired
	private VerificationService verificationService;

	@Autowired
	private EmailService emailService;

	@Override
	@Transactional
	public DataResult<Candidate> save(Candidate requestCandidate) {
		Candidate candidate = null;
		Verification verification = null;
		String token = null;
		String url = "http://localhost:8080/activation?token=";
		try {
			if (this.existsEmployeeByEmail(requestCandidate.getEmail())) {
				return new ErrorDataResult<Candidate>("Bu email kullanımda");
			}
			if (!this.kpsPublicSoapProxy.TCKimlikNoDogrula(requestCandidate.getIdentityNumber(),
					requestCandidate.getName(), requestCandidate.getLastName(), 1992)) {
				return new ErrorDataResult<Candidate>("Tc vatandaş doğrulaması yapılamadı.");
			}
			token = UUID.randomUUID().toString();
			verification = new Verification();
			candidate = this.candidateRepository.save(requestCandidate);
			verification.setUserId(candidate.getId());
			verification.setVerificationCode(token);
			this.verificationService.save(verification);
			this.emailService.sendSimpleMessage(requestCandidate.getEmail(), "Aktivasyon Mail", url + token);

		} catch (Exception e) {
			return new ErrorDataResult<Candidate>(e.getMessage());
		}
		return new SuccessDataResult<Candidate>(requestCandidate);
	}

	@Override
	public boolean existsEmployeeByEmail(String email) {

		return this.candidateRepository.existsByEmail(email);
	}

	@Override
	public DataResult<List<Candidate>> findAll() {
		return new SuccessDataResult<List<Candidate>>(this.candidateRepository.findAll());
	}



}