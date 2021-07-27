package com.kodlamaio.hrms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageAbleRequest {

	private int page;
	
	private int size;
	
	private String sortBy;
}
