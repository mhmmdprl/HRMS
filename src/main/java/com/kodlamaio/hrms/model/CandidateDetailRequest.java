package com.kodlamaio.hrms.model;

import java.util.List;


import com.kodlamaio.hrms.entities.ProgrammingLanguage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateDetailRequest {

	private List<LanguagesRequest> languages ;


	private String gitHubAddress;

	private String linkedInAddress;

	private List<ProgrammingLanguageRequest> programmingLanguages ;

	private String foreword;
	
}
