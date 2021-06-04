package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.Table;


import lombok.Getter;

@Getter
@Entity
@Table(name = "languages")
public class Language extends BaseEntity {
	
	private String lang;

}
