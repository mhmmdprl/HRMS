package com.kodlamaio.hrms.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Employer;

public interface EmployerRepository extends BaseRepository<Employer>,JpaSpecificationExecutor<Employer> {

	@Query("FROM Employer e WHERE e.deleted='0' And lower(e.companyName) Like lower(concat('%', ?1,'%'))  ")
	Page<Employer> findByCompanyNameLike(String searchingWord, Pageable pageable);



	@Query("FROM Employer e WHERE e.deleted='0' and size(e.followers)>2 and e.id not in ?1")
	Page<Employer> findInterestinFollowingEmployer(List<Long> ids,Pageable pageable);

//	@Query("FROM Employer e WHERE e.deleted='0'and e.id in"
//			+ "(Select e.id From Employer e join JopPosting j on e.id=j.employer.id where j.isActive=true and j.deleted='0') ")
//	Page<Employer> findInterestinPostCountEmployer(Pageable pageable);

	@Query("FROM Employer e where e.deleted='0' and e.id not in ?1 ")
	Page<Employer> findInterestinRandEmployer(List<Long> ids,Pageable pageable);

	@Query("Select Count(e) From Employer e where e.deleted='0'")
	int getAllCount();


	@Query("FROM Employer e where exists(select ef From e.followers ef where ef.id=?1) ")
	Page<Employer> getCandidateFollows(Long id, Pageable pageable);


	@Query("FROM Employer e WHERE e.email Like %?1%   ")
	Page<Employer> findByEmailLikeEmployer(String email, Pageable pageable);


	@Query("FROM Employer e WHERE e.managerConfirm=false  ")
	Page<Employer> getPassiveEmployer(Pageable pageable);



	

}
