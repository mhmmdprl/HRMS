package com.kodlamaio.hrms.service.impl;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import com.kodlamaio.hrms.result.GenericResponse;

public class BaseResultService<T> {

	public GenericResponse<T> operationSuccess(T data) {
		GenericResponse<T> response = new GenericResponse<T>();
		response.setData(data);
		response.setSuccess(true);
		response.setResultCode(HttpStatus.OK.value());
		return response;
	}

	public GenericResponse<T> operationFail(Exception e, Logger logger) {
		GenericResponse<T> response = new GenericResponse<T>();
		logger.error("Hata :" + e.getMessage());
		response.setData(null);
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		response.setResultCode(HttpStatus.BAD_REQUEST.value());
		return response;
	}

	public GenericResponse<T> operationFail(Exception e, Logger logger, String message) {
		GenericResponse<T> response = new GenericResponse<T>();
		logger.error("Hata :" + e.getMessage());
		response.setData(null);
		response.setSuccess(false);
		response.setMessage(message);
		response.setMessage(e.getMessage());
		response.setResultCode(HttpStatus.BAD_REQUEST.value());
		return response;
	}
}
