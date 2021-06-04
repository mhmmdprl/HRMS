package com.kodlamaio.hrms.config.beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Configuration
public class Beans {

	@Bean
	public KPSPublicSoapProxy kpsPublicSoapProxy() {
		
		return new KPSPublicSoapProxy();
	}
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
}
