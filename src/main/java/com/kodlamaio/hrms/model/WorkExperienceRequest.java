package com.kodlamaio.hrms.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkExperienceRequest {

	
	private String companyName;
	

	private String businessName;
	

	private String position;


	private Date startingDate;
	

	private Date quitDate;
	
	private String status;
}
