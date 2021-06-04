package com.kodlamaio.hrms.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "programming_languages")
public class ProgrammingLanguage extends BaseEntity{

	private String programmingLanguage;
}
