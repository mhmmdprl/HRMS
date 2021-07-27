package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name = "operations")
public class Operation  extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String path;

	private String method;

	private String description;

	private String code;
}
