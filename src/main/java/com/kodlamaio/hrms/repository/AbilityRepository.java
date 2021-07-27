package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.hrms.entities.Ability;

public interface AbilityRepository extends JpaRepository<Ability, Long>{

	Ability findByAbilityName(String abilityName);

}
