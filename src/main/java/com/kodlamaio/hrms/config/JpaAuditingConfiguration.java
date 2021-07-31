package com.kodlamaio.hrms.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kodlamaio.hrms.service.UserService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {
	
	@Autowired
	private UserService userService; 
	    @Bean
	    public AuditorAware<Long> auditorProvider() {
	    		String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    		if (username.equals("anonymousUser")) {
	    			return ()-> Optional.ofNullable(0l);
	    		}
	        return ()-> Optional.ofNullable(this.userService.findByEmail(username).getId());
	    }

}
