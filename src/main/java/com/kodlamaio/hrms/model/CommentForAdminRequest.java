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
public class CommentForAdminRequest {
	
	private Long id;
	
	private String comment;
	
	private String commentPhoto;
	
	private Date createdDate;
	
	private char deleted;
	
	private MainFeaturesOfTheCandidate candidate;
	

}
