package com.kodlamaio.hrms.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostGetRequest {

	private Long id;
	private String postText;
	private Date createdDate;
	private String postPhoto;
	private MainFeaturesOfTheCandidate candidate;
	private List<MainFeaturesOfTheCandidate> likes;
	private List<CommentRequest> comments;
}
