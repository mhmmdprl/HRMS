package com.kodlamaio.hrms.service.impl;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Comment;
import com.kodlamaio.hrms.entities.Natification;
import com.kodlamaio.hrms.entities.Post;
import com.kodlamaio.hrms.enums.NatificationType;
import com.kodlamaio.hrms.model.CommentForAdminRequest;
import com.kodlamaio.hrms.model.CommentSaveRequest;
import com.kodlamaio.hrms.repository.CommentRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.CommentService;
import com.kodlamaio.hrms.service.NatifacationService;
import com.kodlamaio.hrms.service.PostService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CandidateService candidateService;

	@Autowired
	private PostService postService;

	@Autowired
	private NatifacationService natificationService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemp;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Result save(CommentSaveRequest commentSaveRequest, Long postId, Long candidateId) {

		Candidate candidate = null;
		Post post = null;
		Comment commentObj = null;
		try {
			if (commentSaveRequest.getComment().isEmpty() && commentSaveRequest.getCommentPhoto().isEmpty()) {
				return new ErrorResult("Boş Yorum yapılamaz!");
			}

			commentObj = new Comment();
			candidate = this.candidateService.findByIdForSevices(candidateId);
			post = this.postService.findByIdForOtherService(postId);
			commentObj.setComment(commentSaveRequest.getComment());
			commentObj.setCommentPhoto(commentSaveRequest.getCommentPhoto());
			commentObj.setCandidate(candidate);
			commentObj.setPost(post);
			if (candidateId != post.getCandidate().getId()) {
				Natification natification = new Natification();
				natification.setNatificationType(NatificationType.Comment);
				natification.setToWho(post.getCandidate());
				natification.setWho(candidate);
				this.natificationService.save(natification);
				this.simpMessagingTemp.convertAndSend("/queue/navNatification-" + post.getCandidate().getId(), true);
			}
			post.getComments().add(commentObj);
			this.simpMessagingTemp.convertAndSend("/queue/status-" + post.getCandidate().getId(), true);

			this.commentRepository.save(commentObj);
			this.postService.saveForOtherService(post);
			candidate.getMyConnections().forEach(item -> {
				this.simpMessagingTemp.convertAndSend("/queue/homePage-" + item.getId(), true);
			});

		} catch (Exception e) {

			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Yorum yapıldı");
	}

	@Override
	public Result deleteComment(Long id, Long candidateId) {
		Comment comment = null;
		Candidate candidate = null;
		try {
			candidate = this.candidateService.findByIdForSevices(candidateId);
			comment = this.commentRepository.findByIdSoft(id);
			comment.setDeleted('1');
			this.commentRepository.save(comment);
			this.simpMessagingTemp.convertAndSend("/queue/status-" + comment.getPost().getCandidate().getId(), true);
			candidate.getMyConnections().forEach(item -> {
				this.simpMessagingTemp.convertAndSend("/queue/homePage-" + item.getId(), true);
			});
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Yorum silindi");
	}

	@Override
	public DataResult<Page<CommentForAdminRequest>> getCandidateComments(Long id, Pageable pageable) {

		Page<Comment> pageComment = null;
		Page<CommentForAdminRequest> pageCommentForAdminRequest = null;

		try {
			pageComment = this.commentRepository.getCandidateComments(id, pageable);
			pageCommentForAdminRequest = new PageImpl<CommentForAdminRequest>(pageComment.stream()
					.map(item -> this.modelMapper.map(item, CommentForAdminRequest.class)).collect(Collectors.toList()),
					pageable, pageComment.getTotalElements());

		} catch (Exception e) {
			return new ErrorDataResult<Page<CommentForAdminRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<CommentForAdminRequest>>(pageCommentForAdminRequest);
	}

	@Override
	public Comment findByIdForAdmin(Long id) {
		return this.commentRepository.findById(id).orElseThrow();
	}

	@Override
	public void saveForOtherService(Comment comment) {
		this.commentRepository.save(comment);

	}
}
