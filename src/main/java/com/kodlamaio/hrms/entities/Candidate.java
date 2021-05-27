package com.kodlamaio.hrms.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidates")
public class Candidate extends User {

	private String name;
	
	private String lastName;
	
	private Long identityNumber;
	
	private LocalDate birtOfDate;
	
	

}
