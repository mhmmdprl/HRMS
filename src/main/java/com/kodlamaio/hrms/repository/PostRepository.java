package com.kodlamaio.hrms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	 @Query("Select p From Post p where p.candidate=?1 and p.deleted='0'")
	Page<Post> findByCandidate(Candidate candidate, Pageable pageable);
	
    @Query("Select p From Post p where p.candidate.id in ?1 and p.deleted='0'")
	Page<Post> findMyConnectsPost(List<Long> ids, Pageable pageable);

    @Query("From Post p where p.id=?1 and p.deleted='0'")
	Post findByIdWhereDeleted(Long id);

    @Query("Select Count(p) From Post p where p.deleted='0'")
	int getAllCount();

	 @Query("Select p From Post p where p.candidate=?1")
	Page<Post> getCandidatePostsForAdmin(Candidate candidate, Pageable pageable);

	

}
