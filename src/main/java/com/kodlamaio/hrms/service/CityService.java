package com.kodlamaio.hrms.service;

import java.util.List;

import com.kodlamaio.hrms.entities.City;
import com.kodlamaio.hrms.result.DataResult;

public interface CityService {

	City findById(Long cityId);

	City findByCityName(String cityName);

	DataResult<List<City>> getAll();

}
