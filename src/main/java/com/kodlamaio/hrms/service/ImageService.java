package com.kodlamaio.hrms.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface ImageService {

	DataResult<Map<String, String>> uploadImage(MultipartFile file);
	public Result getImageUrl(MultipartFile file);
}
