package com.kodlamaio.hrms.enums;

import lombok.Getter;

@Getter
public enum NatificationType {

	Connect("connect"),
	Comment("comment"),
	Like("like");
	
	private String value;
	
	NatificationType(String value){
		this.value=value;
	}
}

