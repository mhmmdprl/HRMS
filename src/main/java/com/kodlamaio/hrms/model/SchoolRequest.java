package com.kodlamaio.hrms.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest {

	@NotNull
	@NotBlank
	private String schoolName;
	@NotBlank
	@NotNull
	private String department;
	@Past
	@NotBlank
	@NotBlank
	private Date startingDate;

	private Date quitDate;
	
	private String status;
}
