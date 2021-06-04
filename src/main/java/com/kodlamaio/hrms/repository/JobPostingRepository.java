package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.hrms.entities.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long> {

}
