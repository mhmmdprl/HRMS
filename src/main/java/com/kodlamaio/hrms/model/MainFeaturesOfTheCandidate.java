package com.kodlamaio.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainFeaturesOfTheCandidate {

	private Long id;
	private String name;
	private String lastName;
	private String profilePhoto;
	private String gender;
}
