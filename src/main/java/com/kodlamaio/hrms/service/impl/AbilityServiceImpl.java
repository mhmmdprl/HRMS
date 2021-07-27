package com.kodlamaio.hrms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Ability;
import com.kodlamaio.hrms.repository.AbilityRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.service.AbilityService;

@Service
public class AbilityServiceImpl implements AbilityService {

	@Autowired
	private AbilityRepository abilityRepository;
	@Override
	public Ability findById(Long id) {
		return this.abilityRepository.findById(id).get();
	}
	@Override
	public DataResult<List<Ability>> getAll() {
		return new SuccessDataResult<List<Ability>>(this.abilityRepository.findAll());
	}
	@Override
	public Ability findByName(String abilityName) {
		
		return this.abilityRepository.findByAbilityName(abilityName);
	}

}
