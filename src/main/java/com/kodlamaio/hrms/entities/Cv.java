package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cv extends BaseEntity {

	
	private String cvName;
	@OneToMany(targetEntity = School.class, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "cv_id")
	private List<School> schools = new ArrayList<School>();

	@OneToMany(targetEntity = WorkExperience.class, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "cv_id")
	private List<WorkExperience> experiences = new ArrayList<WorkExperience>();

	@OneToOne(targetEntity = CandidateDetail.class,cascade = CascadeType.ALL)
	private CandidateDetail candidateDetail;

	@ManyToMany
	@JoinTable(name = "cv_abilities", joinColumns = @JoinColumn(name = "cv_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "abilities_id", referencedColumnName = "id"))
	private List<Ability> abilities = new ArrayList<Ability>();
	
	
}
