package com.kodlamaio.hrms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

	
	@Bean
	public Cloudinary getCloudinary() {

		return new Cloudinary(ObjectUtils.asMap(
				"cloud_name", "dtxdu6vbg",
				"api_key", "144188244889313",
				"api_secret", "kI7EtYUFg2SU-b9JlG1jx9J7rVw"));
		
	}
}
