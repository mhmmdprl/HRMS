package com.kodlamaio.hrms.entities;


import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "school")
public class School extends BaseForSchoolAndExperience {

	private String schoolName;

	private String department;



}
