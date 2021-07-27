package com.kodlamaio.hrms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.City;
import com.kodlamaio.hrms.repository.CityRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.service.CityService;
@Service
public class CityServiceImpl implements CityService{

	@Autowired
	private CityRepository cityRepository;
	
	@Override
	public City findById(Long cityId) {
		
		return this.cityRepository.findById(cityId).get();
	}

	@Override
	public City findByCityName(String cityName) {
		// TODO Auto-generated method stub
		return this.cityRepository.findByCityName(cityName);
	}

	@Override
	public DataResult<List<City>> getAll() {
	
		return new SuccessDataResult<List<City>>(this.cityRepository.findAll());
	}

}
