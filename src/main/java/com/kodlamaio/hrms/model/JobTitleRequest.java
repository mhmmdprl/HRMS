package com.kodlamaio.hrms.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobTitleRequest {

	@NotBlank
	private String title;
}
