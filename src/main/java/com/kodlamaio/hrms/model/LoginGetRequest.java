package com.kodlamaio.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginGetRequest {

	private Long id;
	private String role;
}
