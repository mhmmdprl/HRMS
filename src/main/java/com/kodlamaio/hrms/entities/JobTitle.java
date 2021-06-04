package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_titles")
public class JobTitle extends BaseEntity{

	@Column(unique = true)
	private String title;
	
	@OneToMany(mappedBy = "jobTitle",fetch = FetchType.LAZY)
	private List<JobPosting> jobPostings=new ArrayList<JobPosting>();
}
