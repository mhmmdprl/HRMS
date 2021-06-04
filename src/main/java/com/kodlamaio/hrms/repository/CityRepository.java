package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.hrms.entities.City;

public interface CityRepository extends JpaRepository<City,Long> {

	City findByCityName(String jobTitleName);

}
