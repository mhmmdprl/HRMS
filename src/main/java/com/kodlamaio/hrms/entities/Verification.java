package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "verification")
@Where(clause = "deleted='0'")
public class Verification extends BaseEntity{
	
	
	private String verificationCode;
	
	private Long userId;

}
