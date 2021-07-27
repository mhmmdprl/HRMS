package com.kodlamaio.hrms.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.entities.Ability;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.service.AbilityService;


@RequestMapping("api/ability")
@RestController
@CrossOrigin
public class AbilityController {

	@Autowired
	private AbilityService abilityService;
	@GetMapping("/getAll")
	public DataResult<List<Ability>> getALL(){
		
		return this.abilityService.getAll();
	}
	
}
