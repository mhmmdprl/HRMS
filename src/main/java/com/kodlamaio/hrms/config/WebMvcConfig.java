package com.kodlamaio.hrms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.kodlamaio.hrms.filter.OperationFilter;

@Configuration
@EnableWebMvc
@EnableWebSecurity
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private OperationFilter operationFilter;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(this.operationFilter).excludePathPatterns("/**/login", "/**/logout");
	}

  
}
