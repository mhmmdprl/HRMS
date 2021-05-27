package com.kodlamaio.hrms.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {

	protected T data;
	protected String message;
	protected Integer resultCode;
	protected boolean isSuccess;
}
