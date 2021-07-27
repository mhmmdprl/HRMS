package com.kodlamaio.hrms.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.entities.City;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.service.CityService;

@RestController
@RequestMapping("api/city")
@CrossOrigin
public class CityController {

	
	@Autowired
	private   CityService cityService ;

	@GetMapping("getAll")
	public DataResult<List<City>> getAllCity(){
		
		return this.cityService.getAll();
	}
	
}
