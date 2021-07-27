package com.kodlamaio.hrms.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.NatificationGetReques;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.NatifacationService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/natification")
@CrossOrigin
public class NatificationController {

	@Autowired
	private NatifacationService natificationService;
	@Autowired
	private TokenProvider tokenProvider;

	@GetMapping("getMyNatifactions")
	public DataResult<List<NatificationGetReques>> getMyNatifactions(HttpServletRequest httpServletRequest) {
		return this.natificationService.getMyNatifications(this.tokenProvider.getUserIdFromRequest(httpServletRequest));

	}
	
	@PutMapping("allNatificationsSeen")
	public Result allNatificationsSeen(HttpServletRequest httpServletRequest) {
		
		return this.natificationService.allNatificationsSeen(this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
	
	@GetMapping("getHaventSeenNatifications")
	public DataResult<Integer> getHaventSeenNatifications(HttpServletRequest httpServletRequest) {
		
		return this.natificationService.getHaventSeenNatifications(this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
}
