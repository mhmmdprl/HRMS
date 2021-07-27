package com.kodlamaio.hrms.model;


import java.util.List;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CvSaveRequest {

	
	private String cvName;

	List<SchoolRequest> schoolRequests;

	private List<WorkExperienceRequest> experiences;

	private CandidateDetailRequest candidateDetail;

	private List<Long> abilitiyIds;

	
}
