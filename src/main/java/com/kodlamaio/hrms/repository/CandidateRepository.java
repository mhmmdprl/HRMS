package com.kodlamaio.hrms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Candidate;

public interface CandidateRepository extends BaseRepository<Candidate>, JpaSpecificationExecutor<Candidate> {

	boolean existsByIdentityNumber(Long identityNumber);

	@Query("FROM Candidate c where c.deleted='0' And lower(c.name) Like lower(concat('%', ?1,'%')) ")
	Page<Candidate> findByNameLike(String searchingWord, Pageable pageable);

	@Query("FROM Candidate c where c.id!=?1 and c.id not in ?2 and exists (select a From c.myConnections a where a.id  in ?3) ")
	Page<Candidate> getCommonConnects(Long id, List<Long> myConnectionIds, List<Long> ids, Pageable pageable);

	@Query(" FROM Candidate c where ?1 in (select a.id From c.myConnections a ) "
			+ "and ?2 in (select a.id From c.myConnections a ) and c.id!=?1 ")
	Page<Candidate> getMyCommonConnect(Long id, Long id2, Pageable pageable);

	@Query("Select Count(c) From Candidate c where c.deleted='0'")
	int getAllCount();

	@Query("From Candidate c ")
	Page<Candidate> getAllCandidate(Pageable pageable);
	@Query("FROM Candidate c where c.email Like %?1% ")
	Page<Candidate> findByEmailLike(String email, Pageable pageable);

	@Query("From Candidate c where exists(select cf From c.followings cf where cf.id=?1)")
	Page<Candidate> getEmployerFollowers(Long id, Pageable pageable);

}
