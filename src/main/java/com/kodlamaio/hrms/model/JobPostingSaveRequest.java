package com.kodlamaio.hrms.model;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class JobPostingSaveRequest {

	private double maxSalary;

	private double minSalart;

	@NotNull
	@NotBlank
	private int numberOfAvailablePosition;

	@Future
	private Date applicationDeadline;
	
	@NotNull
	@NotBlank
	private Long employerId;
	
	@NotNull
	@NotBlank
	private String jobTitleName;
	
	@NotNull
	@NotBlank
	private String cityName;
	
}
