package com.kodlamaio.hrms.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.kodlamaio.hrms.model.OperationRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.service.OperationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/admin/operation")
@CrossOrigin
public class OperationController {
	@Autowired
	public RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Autowired
	private OperationService operationService;
	
	@ApiOperation("endpoint listesi")
    @GetMapping("/endPoints")
	public DataResult<List<String>> getEndPoints() {
		
		List<String> endPoints = new ArrayList<String>();
		for (RequestMappingInfo a : requestMappingHandlerMapping.getHandlerMethods().keySet()) {
			String path = a.getPatternsCondition().getPatterns().toArray()[0].toString();
			String code = (a.getMethodsCondition().isEmpty() ? "GET"
					: a.getMethodsCondition().getMethods().toArray()[0].toString());

			endPoints.add(path + "_" + code);
		}
		return new SuccessDataResult<List<String>>(endPoints);
	}
	
	@PutMapping("getRoleHasNotOperations")
	public DataResult<List<OperationRequest>> getRoleHasNotOperations(@RequestBody List<OperationRequest> operations,HttpServletRequest httpServletRequest){
		
		
		return this.operationService.getRoleHasNotOperations(operations);
	}
	@PutMapping("getRoleHasOperations")
	public DataResult<List<OperationRequest>> getRoleHasOperations(@RequestBody List<OperationRequest> operations,HttpServletRequest httpServletRequest){
		
		
		return this.operationService.getRoleHasOperations(operations);
	}

}
