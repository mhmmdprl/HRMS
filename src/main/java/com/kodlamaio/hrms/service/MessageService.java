package com.kodlamaio.hrms.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kodlamaio.hrms.entities.Channel;
import com.kodlamaio.hrms.entities.Message;
import com.kodlamaio.hrms.model.MessageGetRequest;
import com.kodlamaio.hrms.model.MessageSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface MessageService {

	Result addMessage(MessageSaveRequest messageRequest,Long id);

	public DataResult<Page<MessageGetRequest>> getMessagesOfChannel(String uuid,Long id, Pageable pageable);
	
	public DataResult<Page<MessageGetRequest>> getCandidateMessagesOfChannel(String uuid,Long id, Pageable pageable);

	List<Message> getAllMessages(Channel channel, Long userIdFromRequest);

	void saveForOtherService(Message item);

	List<Message> getMessagesHaventSeen(Channel item, Long id);

	DataResult<Integer> getHaventSeenMessagesCount(Long userIdFromRequest);

	Result removeMessage(String uuid, Long userIdFromRequest);

}
