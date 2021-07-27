package com.kodlamaio.hrms.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.model.bean.SearchCriteria;

public class EmployerSpecification extends BaseSpecification<Employer> implements Specification<Employer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployerSpecification(List<SearchCriteria> criteriaList) {
		
		super(criteriaList);
	}
	
	@Override
	public Predicate toPredicate(Root<Employer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> filters = new ArrayList<>();
		return super.toPredicate(root, query, builder,filters);	}

}
