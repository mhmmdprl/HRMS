package com.kodlamaio.hrms.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageGetRequest {

	private String uuid;
	private String text;
	private String time;
	private boolean seen;
	private MainFeaturesOfTheCandidate from;


}
