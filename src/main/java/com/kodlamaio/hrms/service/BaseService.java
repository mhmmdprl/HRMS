package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.result.DataResult;

public interface BaseService<T> {

	DataResult<T> save(T entity);
	
}
