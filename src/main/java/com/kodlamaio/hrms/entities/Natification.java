package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.kodlamaio.hrms.enums.NatificationType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Natification extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Candidate.class)
	private Candidate toWho;

	@ManyToOne(targetEntity = Candidate.class)
	private Candidate who;
	private NatificationType natificationType;
	
	private boolean seen=false;
}
