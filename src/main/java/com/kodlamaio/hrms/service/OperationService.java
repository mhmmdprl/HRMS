package com.kodlamaio.hrms.service;


import java.util.List;

import com.kodlamaio.hrms.entities.Operation;
import com.kodlamaio.hrms.model.OperationRequest;
import com.kodlamaio.hrms.result.DataResult;

public interface OperationService {

	Operation findByCode(String code);

	DataResult<List<OperationRequest>> getRoleHasNotOperations(List<OperationRequest> operations);

	Operation findById(Long operationId);

	DataResult<List<OperationRequest>> getRoleHasOperations(List<OperationRequest> operations);


}
