package com.kodlamaio.hrms.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AplicationsRequest {
	
	private Long id;
	private Long employerId;
	private boolean cvMandatory;
	private List<String> criteria;
	private double maxSalary;
	private double minSalary;
	private String postSummary;
	private int numberOfAvailablePosition;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date applicationDeadline;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	private String employerCompanyName;
	private String employerProfilePhoto;
	private String jobTitleTitle;
	private String cityCityName;
	private List<PostApplicationsRequest> postApplications;
	private List<LikesRequest> likes;

}
