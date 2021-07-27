package com.kodlamaio.hrms.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

	
	private Long id;
	private String name;
	private List<OperationRequest> operations;
}
