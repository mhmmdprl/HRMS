package com.kodlamaio.hrms.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CandidateDetailRequest {

	private List<LanguagesRequest> languages ;

	private String gitHubAddress;

	private String linkedInAddress;
	
	private String photo;

	private List<ProgrammingLanguageRequest> programmingLanguages ;

	private String foreword;
	
}
