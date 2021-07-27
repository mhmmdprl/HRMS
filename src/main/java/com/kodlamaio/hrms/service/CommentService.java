package com.kodlamaio.hrms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kodlamaio.hrms.entities.Comment;
import com.kodlamaio.hrms.model.CommentForAdminRequest;
import com.kodlamaio.hrms.model.CommentSaveRequest;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.Result;

public interface CommentService {
	
	Result save(CommentSaveRequest commentSaveRequest,Long candidateId,Long postId);

	Result deleteComment(Long id,Long candidateId);

	DataResult<Page<CommentForAdminRequest>> getCandidateComments(Long id, Pageable pageable);

	Comment findByIdForAdmin(Long id);

	void saveForOtherService(Comment comment);

}