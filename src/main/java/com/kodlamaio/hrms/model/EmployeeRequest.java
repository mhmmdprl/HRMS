package com.kodlamaio.hrms.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {

	private String name;
	
	private String lastName;
	
	private Long identityNumber;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birtOfDate;
	
	private String email;

	private String password;
}
