package com.kodlamaio.hrms.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.MessageSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.MessageService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/message")
@CrossOrigin
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("sendMessage")
	public Result sendMessage(@RequestBody MessageSaveRequest messageSaveRequest,
			HttpServletRequest httpServletRequest) {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		messageSaveRequest.setTime(time);
		return this.messageService.addMessage(messageSaveRequest,
				this.tokenProvider.getUserIdFromRequest(httpServletRequest));

	}
	
	
	@GetMapping ("getHaventSeenMessagesCount")
	DataResult<Integer> getHaventSeenMessagesCount(HttpServletRequest httpServletRequest){
		
		
		return this.messageService.getHaventSeenMessagesCount(this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
	
	@PutMapping("removeMessage")
	public Result removeMessage(@RequestParam String uuid,HttpServletRequest httpServletRequest) {
		
		
	return	this.messageService.removeMessage(uuid,this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}

//	@GetMapping("getMessagesOfChannel")
//	public DataResult<Page<MessageGetRequest>> getMessagesOfChannel(@RequestParam String uuid, @RequestParam int page,
//			@RequestParam int size, HttpServletRequest httpServletRequest) {
//		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").ascending());
//		return this.messageService.getMessagesOfChannel(uuid, pageable,
//				this.tokenProvider.getUserIdFromRequest(httpServletRequest));
//
//	}

}
