package com.kodlamaio.hrms.service;

import com.kodlamaio.hrms.result.Result;

public interface BaseService<T> {

	Result save(T entity);
	
}
