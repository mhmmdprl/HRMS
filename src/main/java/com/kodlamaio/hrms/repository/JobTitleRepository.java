package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.hrms.entities.JobTitle;

public interface JobTitleRepository extends JpaRepository<JobTitle,Long>{

	boolean existsByTitle(String title);

	JobTitle findByTitle(String jobTitleName);

}
