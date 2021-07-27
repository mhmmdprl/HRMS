package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "job_postings")

public class JobPosting extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double maxSalary;
	
	private double minSalary;
	
	private int numberOfAvailablePosition;
	@Temporal(TemporalType.DATE)
	private Date applicationDeadline; 
	
	private String postSummary;
	
	private boolean cvMandatory;
	
	private boolean isActive=true;
	
	
	@ManyToOne
	@JoinColumn(name = "job_title_id",referencedColumnName = "id")
	private JobTitle jobTitle;
	
	@ManyToOne
	@JoinColumn(name = "city_id",referencedColumnName = "id")
	private City city;
	
	@ManyToOne(targetEntity = Employer.class)
	@JoinColumn(name = "employer_id",referencedColumnName = "id")
	private Employer employer;
	
	@ElementCollection
	private List<String> criteria=new ArrayList<String>();

	@ManyToMany(mappedBy = "whatILikes")
	@JsonIgnore
	private List<Candidate> likes=new ArrayList<Candidate>();
	
	@ManyToMany(mappedBy = "applications")
	@JsonIgnore
	private List<Candidate> postApplications=new ArrayList<Candidate>();
	
	
	
}
