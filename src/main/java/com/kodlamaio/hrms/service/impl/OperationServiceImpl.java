package com.kodlamaio.hrms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Operation;
import com.kodlamaio.hrms.model.OperationRequest;
import com.kodlamaio.hrms.repository.OperationRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Operation findByCode(String code) {
		return this.operationRepository.findByCode(code);
	}

	@Override
	public DataResult<List<OperationRequest>> getRoleHasNotOperations(List<OperationRequest> operations) {
		List<Long> ids = null;
		List<Operation> listOperations = null;
		List<OperationRequest> operationRequests = null;
		try {
			ids = operations.stream().map(item -> item.getId()).collect(Collectors.toList());
			if (ids.size() < 1) {
				listOperations = this.operationRepository.findAll();
			} else {
				listOperations = this.operationRepository.getRoleHasNotOperations(ids);
			}

			operationRequests = listOperations.stream().map(item -> this.modelMapper.map(item, OperationRequest.class))
					.collect(Collectors.toList());

		} catch (Exception e) {
			return new ErrorDataResult<List<OperationRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<List<OperationRequest>>(operationRequests);
	}

	@Override
	public Operation findById(Long operationId) {
		return this.operationRepository.findById(operationId).orElseThrow();
	}

	@Override
	public DataResult<List<OperationRequest>> getRoleHasOperations(List<OperationRequest> operations) {
		List<Long> ids = null;
		List<Operation> listOperations = null;
		List<OperationRequest> operationRequests = null;
		try {
			ids = operations.stream().map(item -> item.getId()).collect(Collectors.toList());
			listOperations = this.operationRepository.getRoleHasOperations(ids);
			operationRequests = listOperations.stream().map(item -> this.modelMapper.map(item, OperationRequest.class))
					.collect(Collectors.toList());

		} catch (Exception e) {
			return new ErrorDataResult<List<OperationRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<List<OperationRequest>>(operationRequests);
	}

}
