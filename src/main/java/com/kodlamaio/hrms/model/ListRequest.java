package com.kodlamaio.hrms.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.kodlamaio.hrms.model.bean.SearchCriteria;

import lombok.Data;

@Data
public class ListRequest {
	 
	private List<SearchCriteria> search = new ArrayList<SearchCriteria>();
	private Pageable pageable;
	


}