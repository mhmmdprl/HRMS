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
public class NatificationGetReques {

	
	private MainFeaturesOfTheCandidate candidate;
	
	private String type;
	
	private Date createdDate;
}
