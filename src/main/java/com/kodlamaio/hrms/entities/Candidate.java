package com.kodlamaio.hrms.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "candidates")
public class Candidate extends User {

	private String name;

	private String lastName;

	private Long identityNumber;

	private LocalDate birtOfDate;

	@OneToOne(targetEntity = Cv.class)
	private Cv cv;

	private String gender;

	@ManyToMany()
	@JoinTable(name = "connections", joinColumns = @JoinColumn(name = "request_connect", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "response_connect", referencedColumnName = "id"))
	@JsonIgnore
	private List<Candidate> myConnections = new ArrayList<Candidate>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "followings", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Employer> followings = new ArrayList<Employer>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "likes", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "job_posting_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<JobPosting> whatILikes = new ArrayList<JobPosting>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "post_likes", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Post> whatILikesPost = new ArrayList<Post>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "applications", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "job_posting_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<JobPosting> applications = new ArrayList<JobPosting>();
	
	@OneToMany(mappedBy = "candidate",fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Post> posts=new ArrayList<Post>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "candidate_channels", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "channel_id", referencedColumnName = "id"))
	private List<Channel> messageChannels=new ArrayList<Channel>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "from")
	@JsonIgnore
	private List<Message> messages=new ArrayList<Message>();
}
