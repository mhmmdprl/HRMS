package com.kodlamaio.hrms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String text;
	private String time;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_id", referencedColumnName = "id")
	@JsonIgnore
	private Candidate from;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelOfMessage_id", referencedColumnName = "id")
	@JsonIgnore
	private Channel channelOfMessage;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "thoseWhoDelete", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Candidate> thoseWhoDelete = new ArrayList<Candidate>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "whoSawMessage", joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Candidate> whoSawMessage = new ArrayList<Candidate>();
	
	
}
