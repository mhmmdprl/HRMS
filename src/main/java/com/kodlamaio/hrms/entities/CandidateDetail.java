package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "candidate_details")
public class CandidateDetail extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(targetEntity = Language.class, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "candidate_detail_id")
	private List<Language> languages = new ArrayList<Language>();

	private String photo;

	private String gitHubAddress;

	private String linkedInAddress;

	@OneToMany(targetEntity = ProgrammingLanguage.class, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "candidate_detail_id")
	private List<ProgrammingLanguage> programmingLanguages = new ArrayList<ProgrammingLanguage>();

	private String foreword;
	

	
	
}
