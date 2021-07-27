package com.kodlamaio.hrms.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainFeaturesOfTheEmployer {

	private Long id;
	private String companyName;
	private String webAddress;
	private String phoneNumber;
	private String email;
	private String aboutCompany;
	private char deleted;
	private Date createdDate;
	private boolean lock;
	private String profilePhoto;
	
}
