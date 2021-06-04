package com.kodlamaio.hrms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageAbleRequest {

	private int minPage;
	
	private int maxPage;
	
	private String sortBy;
}
