package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="channels")
public class Channel extends BaseEntity{

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Candidate> candidatesInChannel=new ArrayList<Candidate>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Candidate> participants=new ArrayList<Candidate>();
	
	@OneToMany(mappedBy = "channelOfMessage",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Message> messages=new ArrayList<Message>();
}
