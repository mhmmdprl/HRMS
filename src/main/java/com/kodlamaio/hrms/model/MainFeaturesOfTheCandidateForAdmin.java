package com.kodlamaio.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainFeaturesOfTheCandidateForAdmin {
	
	private Long id;
	private String name;
	private String lastName;
	private String profilePhoto;
	private String gender;
	private String email;
	private char deleted;
	private boolean acctive;
	private boolean lock;

}
