package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Employers")
public class Employer extends User {
	
	private String companyName;
	
	private String webAdress;
	
	private String phoneNumber;
	
	private boolean managerConfirm=false;
	

}
