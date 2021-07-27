package com.kodlamaio.hrms.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.ChannelGetRequest;
import com.kodlamaio.hrms.model.MessageGetRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.ChannelService;
import com.kodlamaio.hrms.service.MessageService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/channel")
@CrossOrigin
public class ChannelController {

	@Autowired
	private ChannelService messageChannelService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private TokenProvider tokenProvider;

	@GetMapping("/getMyChannels")
	public DataResult<Page<ChannelGetRequest>> getMyChannels(@RequestParam int page, @RequestParam int size,
			HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("updatedDate").descending());
		return this.messageChannelService.getMyChannels(this.tokenProvider.getUserIdFromRequest(httpServletRequest),
				pageable);
	}

	@PostMapping("crateChannel")
	public Result createChannel(@RequestParam Long candidateId, HttpServletRequest httpServletRequest) {
		return this.messageChannelService.save(candidateId,
				this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}

	@GetMapping("getMessagesOfChannel")
	public DataResult<Page<MessageGetRequest>> getMessagesOfChannel(@RequestParam String uuid, @RequestParam int page,
			@RequestParam int size, HttpServletRequest httpServletRequest) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
		return this.messageService.getMessagesOfChannel(uuid,
				this.tokenProvider.getUserIdFromRequest(httpServletRequest), pageable);

	}

	@DeleteMapping("deleteMessagesOfChannel")
	public Result deleteMessagesOfChannel(@RequestParam String uuid, HttpServletRequest httpServletRequest) {
		return this.messageChannelService.deleteMessagesOfChannel(uuid,
				this.tokenProvider.getUserIdFromRequest(httpServletRequest));

	}
	@PutMapping("allMessagesSeen")
	public Result allMessagesSeen(@RequestParam String uuid, HttpServletRequest httpServletRequest) {
		
		return this.messageChannelService.allMessagesSeen(uuid,this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
      

}
