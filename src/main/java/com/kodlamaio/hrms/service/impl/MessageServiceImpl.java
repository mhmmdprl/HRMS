package com.kodlamaio.hrms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Message;
import com.kodlamaio.hrms.entities.Channel;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidate;
import com.kodlamaio.hrms.model.MessageGetRequest;
import com.kodlamaio.hrms.model.MessageSaveRequest;
import com.kodlamaio.hrms.repository.MessageRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.ChannelService;
import com.kodlamaio.hrms.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private ChannelService messageChannelService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Result addMessage(MessageSaveRequest messageRequest, Long id) {

		Message message = null;
		Channel messageChannel = null;

		try {
			if(messageRequest.getText().isEmpty()||messageRequest.getChannelUuid().isEmpty()) {
				return new ErrorResult("Boş mesaj gönderilemez");
			}
			Candidate candidate = this.candidateService.findByIdForSevices(messageRequest.getCandidateId());
			messageChannel = this.messageChannelService.findByUuid(messageRequest.getChannelUuid());
			message = new Message();
			message.setChannelOfMessage(messageChannel);
			message.setText(messageRequest.getText());
			message.setFrom(candidate);
			message.setTime(messageRequest.getTime());
			message.getWhoSawMessage().add(candidate);
			List<Candidate> candsInChannel = messageChannel.getCandidatesInChannel().stream()
					.filter(item -> item.getId() != candidate.getId()).collect(Collectors.toList());
			if (!(this.messageChannelService.existsParticipants(messageChannel.getUuid(), candsInChannel.get(0)))) {
				messageChannel.getParticipants().add(candsInChannel.get(0));
				this.messageChannelService.saveForOtherService(messageChannel);
			}
			this.messageRepository.save(message);
			this.simpMessagingTemplate.convertAndSend("/queue/message-" + messageRequest.getChannelUuid(), true);
			this.simpMessagingTemplate.convertAndSend("/queue/channel-" + candsInChannel.get(0).getId(), true);
			this.simpMessagingTemplate.convertAndSend("/queue/navMessages-" + candsInChannel.get(0).getId(), true);
			this.simpMessagingTemplate.convertAndSend("/queue/admin/candidateMessage-" + messageRequest.getChannelUuid(), true);

		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}

		return new SuccessResult("Mesaj Gönderildi");

	}

	@Override
	public DataResult<Page<MessageGetRequest>> getMessagesOfChannel(String uuid,Long id,Pageable pageable) {
		Page<Message> pageMessage = null;
		Page<MessageGetRequest> pageMessageGetRequest = null;
		try {

			pageMessage = this.messageRepository.getMessagesOfChannel(this.messageChannelService.findByUuid(uuid), id,
					pageable);

			pageMessageGetRequest = new PageImpl<MessageGetRequest>(Lists.reverse(pageMessage.stream()
					.map(item -> new MessageGetRequest(item.getUuid(), item.getText(), item.getTime(),item.getWhoSawMessage().size()>1?true:false,
							this.modelMapper.map(item.getFrom(), MainFeaturesOfTheCandidate.class)))
					.collect(Collectors.toList())), pageable, pageMessage.getTotalElements());
			this.simpMessagingTemplate.convertAndSend("/queue/channel-" + this.messageChannelService.findByUuid(uuid)
			.getCandidatesInChannel().stream().filter(item -> item.getId() != id)
			.collect(Collectors.toList()).get(0).getId(), true);
		} catch (Exception e) {
			return new ErrorDataResult<>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<MessageGetRequest>>(pageMessageGetRequest);
	}

	@Override
	public List<Message> getAllMessages(Channel channel, Long userIdFromRequest) {
		
		return this.messageRepository.getMessagesOfChannelList(channel, userIdFromRequest);
	}

	@Override
	public void saveForOtherService(Message item) {
	this.messageRepository.save(item);
		
	}

	@Override
	public List<Message> getMessagesHaventSeen(Channel item, Long id) {
		return this.messageRepository.getMessagesHaventSeen(item, id);
	}

	@Override
	public DataResult<Integer> getHaventSeenMessagesCount(Long userIdFromRequest) {
		List<Channel> channels=null;
		int count =0;
		try {
			channels=this.messageChannelService.getMyChannelsList(userIdFromRequest);
			count=this.messageRepository.getHaventSeenMessagesCount(userIdFromRequest,channels);
			
		} catch (Exception e) {
		return new ErrorDataResult<Integer>("Hata : "+e.getMessage());
		}
		return new SuccessDataResult<Integer>(count);
	}

	@Override
	public Result removeMessage(String uuid, Long userIdFromRequest) {
		Message message=null;
		Candidate candidate=null;
		
		try {
			message=this.messageRepository.findByUuid(uuid);
			candidate=this.candidateService.findByIdForSevices(userIdFromRequest);
			message.getThoseWhoDelete().add(candidate);
			this.messageRepository.save(message);
		} catch (Exception e) {
			return new ErrorResult("Hata : "+e.getMessage());
		}
		return new SuccessResult("Mesaj silindi");
	}

	@Override
	public DataResult<Page<MessageGetRequest>> getCandidateMessagesOfChannel(String uuid, Long id, Pageable pageable) {
		Page<Message> pageMessage = null;
		Page<MessageGetRequest> pageMessageGetRequest = null;
		try {
			pageMessage = this.messageRepository.getCandidateMessagesOfChannel(this.messageChannelService.findByUuid(uuid), id,
					pageable);

			pageMessageGetRequest = new PageImpl<MessageGetRequest>(Lists.reverse(pageMessage.stream()
					.map(item -> new MessageGetRequest(item.getUuid(), item.getText(), item.getTime(),item.getWhoSawMessage().size()>1?true:false,
							this.modelMapper.map(item.getFrom(), MainFeaturesOfTheCandidate.class)))
					.collect(Collectors.toList())), pageable, pageMessage.getTotalElements());
			this.simpMessagingTemplate.convertAndSend("/queue/channel-" + this.messageChannelService.findByUuid(uuid)
			.getCandidatesInChannel().stream().filter(item -> item.getId() != id)
			.collect(Collectors.toList()).get(0).getId(), true);
		} catch (Exception e) {
			return new ErrorDataResult<>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<MessageGetRequest>>(pageMessageGetRequest);
	}

}
