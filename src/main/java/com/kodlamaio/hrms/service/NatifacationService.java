package com.kodlamaio.hrms.service;

import java.util.List;

import com.kodlamaio.hrms.entities.Natification;
import com.kodlamaio.hrms.model.NatificationGetReques;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface NatifacationService {

	public void save(Natification natification);
	
	public DataResult<List<NatificationGetReques>> getMyNatifications(Long userIdFromRequest);

	public Result allNatificationsSeen(Long userIdFromRequest);

	public DataResult<Integer> getHaventSeenNatifications(Long userIdFromRequest);
}
