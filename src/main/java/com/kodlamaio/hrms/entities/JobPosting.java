package com.kodlamaio.hrms.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "job_postings")
public class JobPosting extends BaseEntity {

	private double maxSalary;
	
	private double minSalary;
	
	private int numberOfAvailablePosition;
	
	private Date applicationDeadline; 
	
	@ManyToOne
	@JoinColumn(name = "job_title_id",referencedColumnName = "id")
	private JobTitle jobTitle;
	
	@ManyToOne
	@JoinColumn(name = "city_id",referencedColumnName = "id")
	private City city;
	
	@ManyToOne(targetEntity = Employer.class)
	@JoinColumn(name = "employer_id",referencedColumnName = "id")
	private Employer employer;
	
	private boolean isActive=true;
	
}
