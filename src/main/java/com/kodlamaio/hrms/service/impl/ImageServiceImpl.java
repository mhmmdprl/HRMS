package com.kodlamaio.hrms.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.service.ImageService;

@Service
public class ImageServiceImpl  implements ImageService{

	@Autowired
	private Cloudinary cloudinary;
	
	@Override
	public DataResult<Map<String, String>> uploadImage(MultipartFile multipartFile) {
		File file;
		try {
			file=this.convert(multipartFile);
			 Map<String, String> uploadResult = cloudinary.uploader().upload(file,ObjectUtils.emptyMap());
			file.delete();
			
			 return new SuccessDataResult<Map<String, String>>(uploadResult);
		} catch (IOException e) {
			 return new ErrorDataResult<Map<String, String>>("Upload Hatası : "+e.getMessage());
		}
	
			}
	
	private File convert(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream stream = new FileOutputStream(file);
		stream.write(multipartFile.getBytes());
		stream.close();
		
		return file;
	}
	
	}
	

