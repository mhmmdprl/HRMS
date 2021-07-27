package com.kodlamaio.hrms.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobPostingListRequest {

	public JobPostingListRequest(Long id, List<String> criteria, boolean cvMandatory, double maxSalary,
			double minSalary, String postSummary, int numberOfAvailablePosition, Date applicationDeadline,
			Date createdDate, String employerCompanyName, String employerProfilePhoto, String jobTitleTitle,
			String cityCityName, Long employerId, List<LikesRequest> likes,
			List<PostApplicationsRequest> postApplications) {
		this.id = id;
		this.criteria = criteria;
		this.cvMandatory = cvMandatory;
		this.maxSalary = maxSalary;
		this.minSalary = minSalary;
		this.postSummary = postSummary;
		this.numberOfAvailablePosition = numberOfAvailablePosition;
		this.applicationDeadline = applicationDeadline;
		this.createdDate = createdDate;
		this.employerCompanyName = employerCompanyName;
		this.employerProfilePhoto = employerProfilePhoto;
		this.jobTitleTitle = jobTitleTitle;
		this.cityCityName = cityCityName;
		this.employerId = employerId;
		this.likes = likes;
		this.postApplications = postApplications;
	}

	private Long id;
	private List<String> criteria;
	private boolean cvMandatory;
	private double maxSalary;
	private double minSalary;
	private String postSummary;
	private int numberOfAvailablePosition;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date applicationDeadline;
	private Date createdDate;
	private String employerCompanyName;
	private String employerProfilePhoto;
	private String jobTitleTitle;
	private String cityCityName;
	private Long employerId;
	private char deleted;
	private List<LikesRequest> likes;
	private List<PostApplicationsRequest> postApplications;

}
