package com.kodlamaio.hrms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowingEmployerRequest {
	
	
	private Long id;
	private String email;
	private String companyName;
	private String profilePhoto;
	private String aboutCompany;

}
