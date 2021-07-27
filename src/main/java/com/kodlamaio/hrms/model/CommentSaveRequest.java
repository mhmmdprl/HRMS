package com.kodlamaio.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveRequest {
	
	private String comment;
	
	private String commentPhoto;

}
