package com.kodlamaio.hrms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kodlamaio.hrms.entities.Post;
import com.kodlamaio.hrms.model.PostForAdminGetRequest;
import com.kodlamaio.hrms.model.PostGetRequest;
import com.kodlamaio.hrms.model.PostSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface PostService {
	
	Result save(PostSaveRequest postRequest,Long id);
	DataResult<Page<PostGetRequest>> getMyPosts(Long id,Pageable pageable);
	DataResult<Page<PostForAdminGetRequest>> getCandidatePostsForAdmin(Long id,Pageable pageable);
	DataResult<Page<PostGetRequest>> getMyConnectsPost(Long userIdFromRequest, Pageable pageable);
	Post findByIdForOtherService(Long id);
	void saveForOtherService(Post post);
	Result deletePost(Long id,Long candidateId);
	Result updatePost(PostSaveRequest postUpdateRequest, Long id,Long candidateId);
	int getAllCount();
	Post findByIdForOtherServiceForAdmin(Long id);

}
