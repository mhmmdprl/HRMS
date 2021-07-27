package com.kodlamaio.hrms.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerGetRequest {
	private Long id;
	private String email;
	private String companyName;
	private String webAddress;
	private String phoneNumber;
	private String profilePhoto;
	private String aboutCompany;
	private List<FollowersCandidateRequest> followers;

}
