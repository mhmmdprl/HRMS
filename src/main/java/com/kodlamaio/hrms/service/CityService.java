package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.entities.City;

public interface CityService {

	City findById(Long cityId);

	City findByCityName(String cityName);

}
