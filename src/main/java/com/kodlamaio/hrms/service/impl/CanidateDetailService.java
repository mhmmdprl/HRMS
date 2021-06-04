package com.kodlamaio.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.CandidateDetail;
import com.kodlamaio.hrms.repository.CandidateDetailRepository;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CandidateDetailService;

@Service
public class CanidateDetailService implements CandidateDetailService {

	@Autowired
	private CandidateDetailRepository candidateDetailRepository;

	@Override
	public Result save(CandidateDetail entity) {
		try {
			this.candidateDetailRepository.save(entity);
			return new SuccessResult("Eklendi");
		} catch (Exception e) {
			return new ErrorResult(e.getMessage());
		}

	}

}
