package com.kodlamaio.hrms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long>, JpaSpecificationExecutor<JobPosting> {

	@Query("FROM JobPosting j where j.employer.id=?1 and j.isActive=?2  and j.deleted='0'")
	Page<JobPosting> findActiveOrPassiveJobPosting(Long id, boolean status, Pageable pageable);

	@Query("Select j FROM JobPosting j  where " + " j.isActive=true " + " and j.deleted='0' "
			+ " and j.numberOfAvailablePosition>size(j.postApplications) " + " and j.jobTitle.title IN ?1 "
			+ " and j.applicationDeadline>?2 " + " and j.id NOT IN ?3 ")
	Page<JobPosting> findByJobTitle_TitleIn(List<String> titles, Date date, List<Long> id, Pageable pageable);

	@Query("Select j FROM JobPosting j  where " + " j.isActive=true " + " and j.deleted='0' "
			+ " and j.numberOfAvailablePosition>size(j.postApplications) " + " and size(j.likes)>30"
			+ " and j.applicationDeadline>?1 " + " and j.id NOT IN ?2 ")
	Page<JobPosting> findByLikeCount(Date date, List<Long> candidateAppIds, Pageable pageable);

	@Query("Select j FROM JobPosting j  where " + " j.isActive=true " + " and j.deleted='0' "
			+ " and j.numberOfAvailablePosition>size(j.postApplications) " + " and j.city.cityName IN ?1 "
			+ " and j.applicationDeadline>?2 " + " and j.id NOT IN ?3 ")
	Page<JobPosting> findByCities_CityName(List<String> cities, Date date, List<Long> candidateAppIds,
			Pageable pageable);

	@Query("Select j FROM JobPosting j  where " + " j.isActive=true " + " and j.deleted='0' "
			+ " and j.employer.id  IN ?1 ")

	Page<JobPosting> getFollowedCompanysJobPosting(List<Long> employerIds, Pageable pageable);

	@Query("FROM JobPosting j where j.deleted='0' and lower(j.jobTitle.title) Like lower(concat('%', ?1,'%'))")
	Page<JobPosting> findByJobTitle_TitleLikeIgnoreCase(String searchingWord, Pageable pageable);

	@Query("Select Count(j) From JobPosting j where j.deleted='0'")
	int getAllCount();

	@Query("FROM JobPosting j where exists (select jpa from j.postApplications jpa where jpa.id=?1)")
	Page<JobPosting> getCandidateJobPosting(Long id, Pageable pageable);

	@Query("FROM JobPosting j where j.employer.id=?1 ")
	Page<JobPosting> getEmployerJobPosting(Long id, Pageable pageable);

//	@Query("FROM JobPosting j left join j.postApplications ja  where  j.isActive=true  and j.deleted='0'")
//	Page<JobPosting> findByLikeCounts(Pageable pageable );

}
