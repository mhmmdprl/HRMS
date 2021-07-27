package com.kodlamaio.hrms.model;

import java.util.List;

import javax.validation.Valid;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CvGetRequest {
	
	private Long id;
	private String cvName;

	@Valid
	List<SchoolRequest> schoolRequests;
	@Valid
	private List<WorkExperienceRequest> experiences;

	private CandidateDetailRequest candidateDetail;
	
	private List<AbilityRequest> abilities;

}
