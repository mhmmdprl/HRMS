package com.kodlamaio.hrms.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.CommentSaveRequest;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.CommentService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/save")
	public Result save(@RequestBody CommentSaveRequest commentSaveRequest,@RequestParam Long postId,HttpServletRequest httpServletRequest) {
		
		return this.commentService.save(commentSaveRequest, postId, this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
	@DeleteMapping("deleteComment")
	public Result deleteComment(@RequestParam Long id,HttpServletRequest httpServletRequest) {
		
		return this.commentService.deleteComment(id,this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}

}
