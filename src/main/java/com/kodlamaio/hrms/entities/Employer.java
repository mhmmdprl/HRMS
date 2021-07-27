package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Employers")
public class Employer extends User {

	private String companyName;
	
	private String webAddress;
	
	private String phoneNumber;
	
	private String aboutCompany;
	
	private boolean managerConfirm=false;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "followers", joinColumns = @JoinColumn(name = "employer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Candidate> followers=new ArrayList<Candidate>();
    
}
