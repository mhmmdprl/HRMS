package com.kodlamaio.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateGetConnecRequest {

    private Long id;
    
	private String name;
	
	private String lastName;
	
	private String email;
	
	private String profilePhoto;
	
	private String gender;
}
