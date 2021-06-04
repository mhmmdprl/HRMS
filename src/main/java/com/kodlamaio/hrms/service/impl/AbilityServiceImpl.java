package com.kodlamaio.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Ability;
import com.kodlamaio.hrms.repository.AbilityRepository;
import com.kodlamaio.hrms.service.AbilityService;

@Service
public class AbilityServiceImpl implements AbilityService {

	@Autowired
	private AbilityRepository abilityRepository;
	@Override
	public Ability findById(Long id) {
		return this.abilityRepository.findById(id).orElseThrow();
	}

}
