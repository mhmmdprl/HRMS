package com.kodlamaio.hrms.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobPostingListRequest {



	private double maxSalary;

	private double minSalary;

	private int numberOfAvailablePosition;

	private Date applicationDeadline;
	
	private Date createdDate;
	
	private String employerCompanyName;

	private String jobTitleTitle;

	private String cityCityName;


	
	
}
