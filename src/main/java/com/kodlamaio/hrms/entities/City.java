package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class City extends BaseEntity{

	private String cityName;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private List<JobPosting> jobPostings=new ArrayList<JobPosting>();
	
	
	
}
