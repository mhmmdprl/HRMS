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

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Post;
import com.kodlamaio.hrms.model.CommentRequest;
import com.kodlamaio.hrms.model.MainFeaturesOfTheCandidate;
import com.kodlamaio.hrms.model.PostForAdminGetRequest;
import com.kodlamaio.hrms.model.PostGetRequest;
import com.kodlamaio.hrms.model.PostSaveRequest;
import com.kodlamaio.hrms.repository.PostRepository;
import com.kodlamaio.hrms.result.DataResult;
import com.kodlamaio.hrms.result.ErrorDataResult;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessDataResult;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.CandidateService;
import com.kodlamaio.hrms.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private CandidateService candidateService;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemp;
	@Override
	public Result save(PostSaveRequest postRequest, Long id) {
		Candidate candidate=null;
		Post post = null;
		try {
			candidate=this.candidateService.findByIdForSevices(id);
			post = new Post();
			post.setPostText(postRequest.getPostText());
			post.setCandidate(candidate);
			post.setPostPhoto(postRequest.getPostPhoto());
			this.postRepository.save(post);
			this.simpMessagingTemp.convertAndSend("/queue/status-"+id, true);
			candidate.getMyConnections().forEach(item->{
				this.simpMessagingTemp.convertAndSend("/queue/homePage-"+item.getId(), true);
			});
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Paylaşıldı");
	}

	@Override
	public DataResult<Page<PostGetRequest>> getMyPosts(Long id, Pageable pageable) {
		Candidate candidate = null;
		Page<Post> pagePost = null;
		Page<PostGetRequest> pagePostGetRequest = null;

		try {
			candidate = this.candidateService.findByIdForSevices(id);
			pagePost = this.postRepository.findByCandidate(candidate, pageable);
			pagePostGetRequest = new PageImpl<PostGetRequest>(
					pagePost.stream().map(item -> new PostGetRequest(item.getId(), item.getPostText(),
							item.getCreatedDate(), item.getPostPhoto(),
							this.modelMapper.map(item.getCandidate(), MainFeaturesOfTheCandidate.class),
							item.getLikes().stream()
									.map(cand -> this.modelMapper.map(cand, MainFeaturesOfTheCandidate.class))
									.collect(Collectors.toList()),
							item.getComments().stream()
									.map(comment -> this.modelMapper.map(comment, CommentRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, pagePost.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<PostGetRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<PostGetRequest>>(pagePostGetRequest);
	}



	@Override
	public DataResult<Page<PostGetRequest>> getMyConnectsPost(Long userIdFromRequest, Pageable pageable) {
		Candidate candidate = null;
		Page<Post> pagePost = null;
		Page<PostGetRequest> pagePostGetRequest = null;

		try {
			candidate = this.candidateService.findByIdForSevices(userIdFromRequest);
			List<Long> ids = candidate.getMyConnections().stream().map(Candidate::getId).collect(Collectors.toList());
			ids.add(userIdFromRequest);
			pagePost = this.postRepository.findMyConnectsPost(ids, pageable);
			pagePostGetRequest = new PageImpl<PostGetRequest>(
					pagePost.stream().map(item -> new PostGetRequest(item.getId(), item.getPostText(),
							item.getCreatedDate(), item.getPostPhoto(),
							this.modelMapper.map(item.getCandidate(), MainFeaturesOfTheCandidate.class),
							item.getLikes().stream()
									.map(cand -> this.modelMapper.map(cand, MainFeaturesOfTheCandidate.class))
									.collect(Collectors.toList()),
							item.getComments().stream()
									.map(comment -> this.modelMapper.map(comment, CommentRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, pagePost.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<PostGetRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<PostGetRequest>>(pagePostGetRequest);
	}

	@Override
	public Post findByIdForOtherService(Long id) {
		return this.postRepository.findByIdWhereDeleted(id);
	}

	@Override
	public void saveForOtherService(Post post) {
		this.postRepository.save(post);
	}

	@Override
	public Result deletePost(Long id,Long candidateId) {
		Post post = null;
		Candidate candidate=null;
		try {
			post = this.postRepository.findByIdWhereDeleted(id);
			post.setDeleted('1');
			this.postRepository.save(post);
			candidate=this.candidateService.findByIdForSevices(candidateId);
			this.simpMessagingTemp.convertAndSend("/queue/status-"+candidateId, true);
			candidate.getMyConnections().forEach(item->{
				this.simpMessagingTemp.convertAndSend("/queue/homePage-"+item.getId(), true);
			});
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Silindi");
	}

	@Override
	public Result updatePost(PostSaveRequest postUpdateRequest, Long id,Long candidateId) {
		Candidate candidate=null;
		Post post=null;
		try {
			post=this.postRepository.findByIdWhereDeleted(id);
			post.setPostText(postUpdateRequest.getPostText());
			post.setPostPhoto(postUpdateRequest.getPostPhoto());
			candidate=this.candidateService.findByIdForSevices(candidateId);
			this.postRepository.save(post);
			this.simpMessagingTemp.convertAndSend("/queue/status-"+candidateId, true);
			candidate.getMyConnections().forEach(item->{
				this.simpMessagingTemp.convertAndSend("/queue/homePage-"+item.getId(), true);
			});
		} catch (Exception e) {
     return new ErrorResult("Hata : "+e.getMessage());
		}
		return new SuccessResult("Güncellendi");
	}

	@Override
	public int getAllCount() {
		
		return this.postRepository.getAllCount();
	}

	@Override
	public DataResult<Page<PostForAdminGetRequest>> getCandidatePostsForAdmin(Long id, Pageable pageable) {
		Candidate candidate = null;
		Page<Post> pagePost = null;
		Page<PostForAdminGetRequest> pagePostGetRequest = null;

		try {
			candidate = this.candidateService.findByIdForSevices(id);
			pagePost = this.postRepository.getCandidatePostsForAdmin(candidate, pageable);
			pagePostGetRequest = new PageImpl<PostForAdminGetRequest>(
					pagePost.stream().map(item -> new PostForAdminGetRequest(item.getId(), item.getPostText(),
							item.getCreatedDate(), item.getPostPhoto(),item.getDeleted(),
							this.modelMapper.map(item.getCandidate(), MainFeaturesOfTheCandidate.class),
							item.getLikes().stream()
									.map(cand -> this.modelMapper.map(cand, MainFeaturesOfTheCandidate.class))
									.collect(Collectors.toList()),
							item.getComments().stream()
									.map(comment -> this.modelMapper.map(comment, CommentRequest.class))
									.collect(Collectors.toList())))
							.collect(Collectors.toList()),
					pageable, pagePost.getTotalElements());
		} catch (Exception e) {
			return new ErrorDataResult<Page<PostForAdminGetRequest>>("Hata : " + e.getMessage());
		}
		return new SuccessDataResult<Page<PostForAdminGetRequest>>(pagePostGetRequest);
	}

	@Override
	public Post findByIdForOtherServiceForAdmin(Long id) {
		return this.postRepository.findById(id).get();
	}

}
