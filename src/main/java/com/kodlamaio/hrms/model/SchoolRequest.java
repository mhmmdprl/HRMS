package com.kodlamaio.hrms.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest {

	private Long id;
	@NotNull
	@NotBlank
	private String schoolName;
	@NotBlank
	@NotNull
	private String department;
	@Past
	@NotBlank
	@NotBlank
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd", locale = "tr-TR")
	private Date startingDate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date quitDate;
	
	private String status;
}
