package com.kodlamaio.hrms.service;

import java.util.List;

import com.kodlamaio.hrms.entities.Ability;
import com.kodlamaio.hrms.result.DataResult;

public interface AbilityService {

	Ability findById(Long id);
	DataResult<List<Ability>> getAll();
	Ability findByName(String abilityName); 
}
