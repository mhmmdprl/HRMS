package com.kodlamaio.hrms.entities;


import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "work_experiences")
public class WorkExperience extends BaseForSchoolAndExperience{

	private String companyName;
	

	private String businessName;
	

	private String position;

	
}
