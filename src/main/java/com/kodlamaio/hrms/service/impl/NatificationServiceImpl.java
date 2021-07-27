package com.kodlamaio.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Natification;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidate;
import com.kodlamaio.hrms.model.NatificationGetReques;
import com.kodlamaio.hrms.repository.NatificationRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.NatifacationService;

@Service
public class NatificationServiceImpl implements NatifacationService{

	@Autowired
	private NatificationRepository natificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private CandidateService candidateService;
	@Override
	public void save(Natification natification) {
		
		this.natificationRepository.save(natification);
	}

	@Override
	public DataResult<List<NatificationGetReques>> getMyNatifications(Long userIdFromRequest) {
		List<Natification> natifications=null;
		List<NatificationGetReques> list=new ArrayList<NatificationGetReques>();
		
		try {
			natifications=this.natificationRepository.findByToWho_Id(userIdFromRequest);
		     
			natifications.forEach(natification->{
				NatificationGetReques natificationGetReques=new NatificationGetReques();
				natificationGetReques.setCandidate(this.modelMapper.map(natification.getWho(), MainFeaturesOfTheCandidate.class));
				natificationGetReques.setCreatedDate(natification.getCreatedDate());
				natificationGetReques.setType(natification.getNatificationType().getValue());
				list.add(natificationGetReques);
			});
			
			
		} catch (Exception e) {
		return new ErrorDataResult<>("Hata : "+e.getMessage());
		}
		return new SuccessDataResult<List<NatificationGetReques>>(Lists.reverse(list));
	}

	@Override
	public Result allNatificationsSeen(Long userIdFromRequest) {
		List<Natification> natifications=null;
		Candidate candidate=null;
		try {
			candidate=this.candidateService.findByIdForSevices(userIdFromRequest);
			natifications=this.natificationRepository.getHaventSeenNatification(candidate);
			natifications.forEach(item->{
				item.setSeen(true);
				this.natificationRepository.save(item);
			});
		} catch (Exception e) {
			return new ErrorResult("Hata : "+e.getMessage());
		}
		return new SuccessResult("Tüm bilidirimler görüldü");
	}

	@Override
	public DataResult<Integer> getHaventSeenNatifications(Long userIdFromRequest) {
		int count=0;
		try {
			count=this.natificationRepository.getHaventSeenNatificationCount(this.candidateService.findByIdForSevices(userIdFromRequest));
		} catch (Exception e) {
		return new ErrorDataResult<Integer>("Hata : "+e.getMessage());
		}
		return new SuccessDataResult<Integer>(count);
	}
	
}
