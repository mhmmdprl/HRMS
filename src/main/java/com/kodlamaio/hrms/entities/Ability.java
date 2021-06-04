package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ability extends BaseEntity {

	
	private String abilityName;

}
