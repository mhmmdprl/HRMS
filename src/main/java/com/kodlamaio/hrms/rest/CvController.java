package com.kodlamaio.hrms.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.model.CvGetRequest;
import com.kodlamaio.hrms.model.CvSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.CvService;

@RestController
@RequestMapping("/cv")
public class CvController {

	@Autowired
	private CvService cvService;

	@PostMapping(path = "/save")
	public Result save(@RequestBody CvSaveRequest cvSaveRequest) {

		return this.cvService.save(cvSaveRequest);
	}

	@GetMapping("/getALL")
	public DataResult<List<CvGetRequest>> getAll() {

		return this.cvService.getAll();
	}

	@PostMapping("/addImage")
	public Result addImage(@RequestParam MultipartFile file, @RequestParam Long id) {

		return this.cvService.addImage(file, id);
	}

}
