package com.kodlamaio.hrms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PostApplicationsRequest {

	private Long id;
	private String name;
	private String lastName;
	private String email;
	private String profilePhoto;
}
