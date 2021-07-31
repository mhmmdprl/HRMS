package com.kodlamaio.hrms.service;

import java.util.List;

import com.kodlamaio.hrms.entities.Role;
import com.kodlamaio.hrms.model.RoleRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface RoleService {

	DataResult<List<RoleRequest>> getRoles();

	Result addOperation(Long roleId, Long operationId);

	Result deleteOperation(Long roleId, Long operationId);

	Role findById(long id);
	
    Role findByCode(String code);
}
