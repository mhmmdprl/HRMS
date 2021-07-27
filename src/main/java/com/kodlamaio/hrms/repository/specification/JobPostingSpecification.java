package com.kodlamaio.hrms.repository.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.kodlamaio.hrms.entities.City;
import com.kodlamaio.hrms.entities.JobPosting;
import com.kodlamaio.hrms.model.bean.SearchCriteria;

public class JobPostingSpecification extends BaseSpecification<JobPosting> implements Specification<JobPosting> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<SearchCriteria> criteriaList;

	public JobPostingSpecification(List<SearchCriteria> criteriaList) {
		super(criteriaList);
		this.criteriaList = criteriaList;
	}

	@Override
	public Predicate toPredicate(Root<JobPosting> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> filters = new ArrayList<>();
		for (SearchCriteria criteria : criteriaList) {
			if (criteria.getOperation().equalsIgnoreCase("nOfAP")) {
				filters.add(builder.lessThan(builder.size(root.get("postApplications")),
						root.get("numberOfAvailablePosition")));
			} else if (criteria.getOperation().equalsIgnoreCase("applicationDeadline")) {
				filters.add(builder.greaterThan(root.<Date>get("applicationDeadline"), new Date()));
			} else if (criteria.getOperation().equalsIgnoreCase("avaibleForApply")) {
//				Join<JobPosting, Candidate> groupJoin = root.join("postApplications");
//				filters.add(
//						builder.equal(groupJoin.get("id"), Long.parseLong(criteria.getValue().toString())).not());
				@SuppressWarnings("unchecked")
				List<String> list=(List<String>) criteria.getValue();
				filters.add(root.get("id").in(list).not());

			} else if (criteria.getOperation().equalsIgnoreCase("cityForJobPost")) {
				Join<JobPosting, City> groupJoin = root.join("city");
				filters.add(groupJoin.get("cityName").in(criteria.getValue()));
			} else if (criteria.getOperation().equalsIgnoreCase("jobTitleForJobPost")) {
				Join<JobPosting, City> groupJoin = root.join("jobTitle");
				filters.add(groupJoin.get("title").in(criteria.getValue()));
			} else if (criteria.getOperation().equalsIgnoreCase("=bool")) {
				filters.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			}
		}

		return super.toPredicate(root, query, builder, filters);
	}

}
