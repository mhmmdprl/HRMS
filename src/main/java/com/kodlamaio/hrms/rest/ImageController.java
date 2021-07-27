package com.kodlamaio.hrms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.ImageService;

@RestController
@RequestMapping("api/image")
@CrossOrigin
public class ImageController {

	@Autowired
	private ImageService imageService;
	@PostMapping("/getImageUrl")
	public Result getImageUrl(@RequestParam("file") MultipartFile file) {

		return this.imageService.getImageUrl(file);
	}
}
