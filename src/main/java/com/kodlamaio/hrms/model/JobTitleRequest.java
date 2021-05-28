package com.kodlamaio.hrms.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class JobTitleRequest {

	@NotBlank
	private String title;
}
