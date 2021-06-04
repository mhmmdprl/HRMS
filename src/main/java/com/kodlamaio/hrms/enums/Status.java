package com.kodlamaio.hrms.enums;

import lombok.Getter;

@Getter
public enum Status {

	
	RESUM("Devam ediyor"),
	GRADUATED("Mezun"),
	LEAVED("İşten Ayrıldı");
	
	
	private String value;
	
	Status(String value){
		this.value=value;
	}
}
