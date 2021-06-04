package com.kodlamaio.hrms.model;


import java.util.List;


import lombok.Getter;

@Getter
public class CvSaveRequest {

	private Long candidateId;
	
	private String cvName;

	List<SchoolRequest> schoolRequests;

	private List<WorkExperienceRequest> experiences;

	private CandidateDetailRequest candidateDetail;

	private List<Long> abilitiyIds;

	
}
