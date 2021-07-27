package com.kodlamaio.hrms.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Channel;
import com.kodlamaio.hrms.entities.Message;
import com.kodlamaio.hrms.model.ChannelGetRequest;
import com.kodlamaio.hrms.model.HaventSeenMessagesRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidate;
import com.kodlamaio.hrms.model.MessageGetRequest;
import com.kodlamaio.hrms.repository.ChannelRepository;
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
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelRepository messageChannelRepository;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public DataResult<Page<ChannelGetRequest>> getMyChannels(Long id, Pageable pageable) {
		Page<ChannelGetRequest> pageChannelGetRequest = null;
		try {
			Page<Channel> pageChannel = this.messageChannelRepository.findMyChannels(id, pageable);
			pageChannelGetRequest = new PageImpl<ChannelGetRequest>(
					pageChannel
							.stream().map(item -> new ChannelGetRequest(item.getUuid(),
									item.getCandidatesInChannel().stream()
											.map(cand -> this.modelMapper.map(cand, MainFeaturesOfTheCandidate.class))
											.collect(Collectors.toList()),
									new HaventSeenMessagesRequest(
											this.messageService.getMessagesHaventSeen(item, id).size() > 0
													? this.messageService.getMessagesHaventSeen(item, id).size()
													: 0,
											this.messageService.getMessagesHaventSeen(item, id).size() > 0
													? this.messageService.getMessagesHaventSeen(item, id)
															.get(this.messageService.getMessagesHaventSeen(item, id)
																	.size() - 1)
															.getText()
													: ""),
									this.messageService.getAllMessages(item, id).size() > 0 ? this.messageService
											.getAllMessages(item, id)
											.get(this.messageService.getAllMessages(item, id).size() - 1).getText()
											: ""))
							.collect(Collectors.toList()),
					pageable, pageChannel.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<ChannelGetRequest>>("Hata : " + e.getMessage());
		}

		return new SuccessDataResult<Page<ChannelGetRequest>>(pageChannelGetRequest);

	}

	@Override
	public Channel findByUuid(String uuid) {

		return this.messageChannelRepository.findByUuid(uuid);
	}

	@Override
	public Result save(Long candidateId, Long id) {
		Candidate candidateTo = null;
		Candidate candidateFrom = null;
		Channel channel = null;
		try {
			
			candidateTo = this.candidateService.findByIdForSevices(candidateId);
			candidateFrom = this.candidateService.findByIdForSevices(id);

			if (this.messageChannelRepository.existsByToId(candidateTo.getId(), candidateFrom.getId())) {
				channel=this.messageChannelRepository.getChannel(candidateTo.getId(), candidateFrom.getId());
				if(!(channel.getParticipants().contains(candidateFrom))) {
					channel.getParticipants().add(candidateFrom);
					this.messageChannelRepository.save(channel);
					return new SuccessResult(channel.getUuid());
				}
				return new SuccessResult(channel.getUuid());
			}
			channel=new Channel();
			channel.setCandidatesInChannel(Arrays.asList(candidateFrom, candidateTo));
			channel.setParticipants(Arrays.asList(candidateFrom));
			this.messageChannelRepository.save(channel);
			this.simpMessagingTemplate.convertAndSend("/queue/admin/channel-" + id, true);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult(channel.getUuid());
	}

	@Override
	public DataResult<Channel> getChannel(String uuid) {
		return new SuccessDataResult<Channel>(this.messageChannelRepository.findByUuid(uuid));
	}

	@Override
	public void saveForOtherService(Channel messageChannel) {
		this.messageChannelRepository.save(messageChannel);
	}

	@Override
	public DataResult<Page<MessageGetRequest>> getMessagesOfChannel(String uuid, Pageable pageable,
			Long userIdFromToken) {
		Page<Message> pageMessage = null;
		Page<MessageGetRequest> pageMessageGetRequest = null;
		try {

			pageMessage = this.messageChannelRepository.getMessagesOfChannel(uuid, pageable);

			pageMessageGetRequest = new PageImpl<MessageGetRequest>(pageMessage.stream()
					.map(item -> new MessageGetRequest(item.getUuid(), item.getText(), item.getTime(),item.getWhoSawMessage().size()>1?true:false,
							this.modelMapper.map(item.getFrom(), MainFeaturesOfTheCandidate.class)))
					.collect(Collectors.toList()), pageable, pageMessage.getTotalElements());
//			this.simpMessagingTemplate.convertAndSend("/queue/channel-" + this.messageChannelRepository.findByUuid(uuid)
//					.getCandidatesInChannel().stream().filter(item -> item.getId() != userIdFromToken)
//					.collect(Collectors.toList()).get(0).getId(), true);
		} catch (Exception e) {
			return new ErrorDataResult<>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<MessageGetRequest>>(pageMessageGetRequest);
	}

	@Override
	public boolean existsParticipants(String uuid, Candidate candidate) {

		return this.messageChannelRepository.existsParticipants(uuid, candidate.getId());
	}

	@Override
	public Result deleteMessagesOfChannel(String uuid, Long userIdFromRequest) {
		List<Message> messages = null;
		Channel channel = null;
		try {
			messages = this.messageService.getAllMessages(this.messageChannelRepository.findByUuid(uuid),
					userIdFromRequest);
			channel = this.messageChannelRepository.findByUuid(uuid);
			messages.forEach(item -> {
				item.getThoseWhoDelete().add(this.candidateService.findByIdForSevices(userIdFromRequest));
				this.messageService.saveForOtherService(item);
			});
			channel.getParticipants().removeIf(item -> item.getId() == userIdFromRequest);
			this.messageChannelRepository.save(channel);
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Mesajlar Silindi");
	}

	@Override
	public Result allMessagesSeen(String uuid, Long userIdFromRequest) {
		List<Message> messages = null;
		try {
			messages = this.messageService.getMessagesHaventSeen(this.messageChannelRepository.findByUuid(uuid),
					userIdFromRequest);
			messages.forEach(item -> {
				item.getWhoSawMessage().add(this.candidateService.findByIdForSevices(userIdFromRequest));
				this.messageService.saveForOtherService(item);
			});
			this.simpMessagingTemplate.convertAndSend("/queue/message-" +uuid, true);
		} catch (Exception e) {
			return new ErrorResult("Hata :" + e.getMessage());
		}
		return new SuccessResult("Mesajlar Görüldü");
	}

	@Override
	public List<Channel> getMyChannelsList(Long userIdFromRequest) {
		
		return this.messageChannelRepository.getMyChannelsList(userIdFromRequest);
	}

	@Override
	public DataResult<Page<ChannelGetRequest>> getCandidateChannels(Long id, Pageable pageable) {
		Page<ChannelGetRequest> pageChannelGetRequest = null;
		try {
			Page<Channel> pageChannel = this.messageChannelRepository.getCandidateChannels(id, pageable);
			pageChannelGetRequest = new PageImpl<ChannelGetRequest>(
					pageChannel
							.stream().map(item -> new ChannelGetRequest(item.getUuid(),
									item.getCandidatesInChannel().stream()
											.map(cand -> this.modelMapper.map(cand, MainFeaturesOfTheCandidate.class))
											.collect(Collectors.toList()),
									new HaventSeenMessagesRequest(
											this.messageService.getMessagesHaventSeen(item, id).size() > 0
													? this.messageService.getMessagesHaventSeen(item, id).size()
													: 0,
											this.messageService.getMessagesHaventSeen(item, id).size() > 0
													? this.messageService.getMessagesHaventSeen(item, id)
															.get(this.messageService.getMessagesHaventSeen(item, id)
																	.size() - 1)
															.getText()
													: ""),
									this.messageService.getAllMessages(item, id).size() > 0 ? this.messageService
											.getAllMessages(item, id)
											.get(this.messageService.getAllMessages(item, id).size() - 1).getText()
											: ""))
							.collect(Collectors.toList()),
					pageable, pageChannel.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<ChannelGetRequest>>("Hata : " + e.getMessage());
		}

		return new SuccessDataResult<Page<ChannelGetRequest>>(pageChannelGetRequest);
	}

}
