package com.kodlamaio.hrms.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.entities.Cv;
import com.kodlamaio.hrms.model.CvGetRequest;
import com.kodlamaio.hrms.model.CvSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface CvService {

	Result save(CvSaveRequest cvSaveRequest);

	DataResult<List<CvGetRequest>> getAll();

	Result addImage(MultipartFile file, Long id);
}
