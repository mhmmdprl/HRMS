package com.kodlamaio.hrms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

	@Query("From Comment c where c.deleted='0' and c.id=?1")
	Comment findByIdSoft(Long id);

	@Query("From Comment c where c.candidate.id=?1")
	Page<Comment> getCandidateComments(Long id, Pageable pageable);

}
