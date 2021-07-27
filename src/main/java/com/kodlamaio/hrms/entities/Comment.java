package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Where(clause = "deleted='0'")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String comment;
	
	private String commentPhoto;
	
	@ManyToOne(targetEntity = Candidate.class)
	private Candidate candidate;
	
	@ManyToOne
    private Post post;
}
