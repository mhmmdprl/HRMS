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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.model.PostGetRequest;
import com.kodlamaio.hrms.model.PostSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.service.PostService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/post")
@CrossOrigin
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/save")
	public Result savePost(@RequestBody PostSaveRequest postSaveRequest, HttpServletRequest httpServletRequest) {

		return this.postService.save(postSaveRequest, this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}

	@GetMapping("/getMyPosts")
	public DataResult<Page<PostGetRequest>> getMyPosts(@RequestParam int page, @RequestParam int size,
			HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.postService.getMyPosts(this.tokenProvider.getUserIdFromRequest(httpServletRequest), pageable);
	}
	@GetMapping("/getCandidatePosts")
	public DataResult<Page<PostGetRequest>> getCandidatePosts(@RequestParam int page, @RequestParam int size,@RequestParam Long id,
			HttpServletRequest httpServletRequest) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.postService.getMyPosts(id, pageable);
	}
	
	@GetMapping("/getMyConnectsPost")
	public DataResult<Page<PostGetRequest>> getMyConnectsPost(@RequestParam int page, @RequestParam int size,
			HttpServletRequest httpServletRequest){
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		return this.postService.getMyConnectsPost(this.tokenProvider.getUserIdFromRequest(httpServletRequest), pageable);
	}
	@DeleteMapping("deletePost")
	public Result deletePost(@RequestParam Long id,HttpServletRequest httpServletRequest) {
		
		return this.postService.deletePost(id,this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
	@PutMapping("updatePost")
	public Result updatePost(@RequestBody PostSaveRequest postUpdateRequest, @RequestParam Long id,HttpServletRequest httpServletRequest) {
		
		return this.postService.updatePost(postUpdateRequest,id,this.tokenProvider.getUserIdFromRequest(httpServletRequest));
	}
}
