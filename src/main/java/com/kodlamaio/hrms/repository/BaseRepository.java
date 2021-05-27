package com.kodlamaio.hrms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

	public boolean existsByEmail(String email);
	
	public T findByEmail(String email);
}
