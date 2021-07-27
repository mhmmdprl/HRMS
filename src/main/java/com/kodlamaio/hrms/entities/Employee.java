package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Setter
@Entity
@Table(name = "employees")
@Getter
public class Employee extends User {

	
	private String firstName;
	
	private String lastName;

}
