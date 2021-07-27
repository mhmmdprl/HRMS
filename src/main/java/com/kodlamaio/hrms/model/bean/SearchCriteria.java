package com.kodlamaio.hrms.model.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria implements Serializable {	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
    private String operation;
    private Object value;
    private Object secondValue;

}
