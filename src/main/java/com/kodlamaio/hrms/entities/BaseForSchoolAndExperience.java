package com.kodlamaio.hrms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "base_for_school_and_experience")
public class BaseForSchoolAndExperience {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	private Date startingDate;

	private Date quitDate;
	
	private boolean isAvtice;
	
	private String status;
}
