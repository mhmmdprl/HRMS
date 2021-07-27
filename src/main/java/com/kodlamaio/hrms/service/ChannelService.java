package com.kodlamaio.hrms.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Channel;
import com.kodlamaio.hrms.model.ChannelGetRequest;
import com.kodlamaio.hrms.model.MessageGetRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;


public interface ChannelService {

	
	

	public DataResult<Page<ChannelGetRequest>> getMyChannels(Long id,Pageable pageable);
	
	public DataResult<Page<ChannelGetRequest>> getCandidateChannels(Long id,Pageable pageable);

	public Channel findByUuid(String  uuid);

	public Result save(Long candidateId,Long id);
	
	public DataResult<Channel> getChannel(String uuid);

	public void saveForOtherService(Channel messageChannel);

	public DataResult<Page<MessageGetRequest>> getMessagesOfChannel(String uuid, Pageable pageable,Long id);

	public boolean existsParticipants(String uuid,Candidate candidate);

	public Result deleteMessagesOfChannel(String uuid, Long userIdFromRequest);

	public Result allMessagesSeen(String uuid, Long userIdFromRequest);

	public List<Channel> getMyChannelsList(Long userIdFromRequest);

}
