package com.kodlamaio.hrms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Operation;
import com.kodlamaio.hrms.entities.Role;
import com.kodlamaio.hrms.model.RoleRequest;
import com.kodlamaio.hrms.repository.RoleRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.OperationService;
import com.kodlamaio.hrms.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private OperationService operationService;

	@Override
	public DataResult<List<RoleRequest>> getRoles() {

		return new SuccessDataResult<List<RoleRequest>>(this.roleRepository.findAll().stream()
				.map(item -> this.modelMapper.map(item, RoleRequest.class)).collect(Collectors.toList()));
	}

	@Override
	public Result addOperation(Long roleId, Long operationId) {
		Operation operation = null;
		Role role = null;
		try {
			role = this.roleRepository.findById(roleId).get();
			operation = this.operationService.findById(operationId);
			if (role.getOperations().contains(operation)) {
				return new ErrorResult("Operasyon zaten mevcut");
			}
             role.getOperations().add(operation);
             this.roleRepository.save(role);
		} catch (Exception e) {
			return new ErrorResult("Hata : "+e.getMessage());
		}
		return new SuccessResult("Operasyon Eklendi");
	}

	@Override
	public Result deleteOperation(Long roleId, Long operationId) {
		Operation operation = null;
		Role role = null;
		try {
			role = this.roleRepository.findById(roleId).get();
			operation = this.operationService.findById(operationId);
			if (!role.getOperations().contains(operation)) {
				return new ErrorResult("Operasyon zaten mevcut değil");
			}
		    role.getOperations().removeIf(item->item.getId()==operationId);
		    this.roleRepository.save(role);
		} catch (Exception e) {
			return new ErrorResult("Hata : "+e.getMessage());
		}
		return new SuccessResult("Operasyon silindi");
	}

	@Override
	public Role findById(long id) {
		return this.roleRepository.findById(id).get();
	}

}
