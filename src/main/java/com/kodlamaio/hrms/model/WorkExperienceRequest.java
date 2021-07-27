package com.kodlamaio.hrms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkExperienceRequest {

	
	private String companyName;


	private String position;

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startingDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date quitDate;
	
	private String status;
}
