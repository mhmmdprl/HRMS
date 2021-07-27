package com.kodlamaio.hrms.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateGetRequest {

	public CandidateGetRequest(Long id, String name, String lastName, LocalDate birtOfDate, String email, String gender,
			String profilePhoto, List<CandidateGetConnecRequest> myConnections,
			List<FollowingEmployerRequest> followings, List<AplicationsRequest> aplications,Date createdDate) {

		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.birtOfDate = birtOfDate;
		this.email = email;
		this.gender = gender;
		this.profilePhoto = profilePhoto;
		this.myConnections = myConnections;
		this.followings = followings;
		this.applications = aplications;
		this.createdDate=createdDate;
	}

	private Long id;

	private String name;

	private String lastName;

	private LocalDate birtOfDate;

	private String email;

	private String gender;

	private String profilePhoto;

	private Long identityNumber;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date createdDate;

	private List<CandidateGetConnecRequest> myConnections;

	private List<FollowingEmployerRequest> followings;

	private List<AplicationsRequest> applications;
	
	private boolean hasCv;

}
