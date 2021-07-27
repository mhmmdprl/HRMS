package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String postText;
	private String postPhoto;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Candidate candidate;
	@ManyToMany(mappedBy = "whatILikesPost")
	@JsonIgnore
	private List<Candidate> likes=new ArrayList<Candidate>();
	
	@OneToMany(mappedBy = "post")
	private List<Comment> comments=new ArrayList<Comment>();
	
}
