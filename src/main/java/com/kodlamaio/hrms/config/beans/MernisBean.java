package com.kodlamaio.hrms.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Configuration
public class MernisBean {

	@Bean
	public KPSPublicSoapProxy kpsPublicSoapProxy() {
		
		return new KPSPublicSoapProxy();
	}
	
}
