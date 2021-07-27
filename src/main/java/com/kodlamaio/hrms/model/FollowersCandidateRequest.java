package com.kodlamaio.hrms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowersCandidateRequest {
	
	private Long id;
	private String profilePhoto;
	private String email;
	private String name;
	private String lastName;
	private String gender;

}
