package com.kodlamaio.hrms.repository.specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.kodlamaio.hrms.model.bean.SearchCriteria;

public class BaseSpecification<T> {

	protected List<SearchCriteria> criteriaList;
	

	public BaseSpecification(List<SearchCriteria> criteriaList) {
		this.criteriaList = criteriaList;
	}


	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,List<Predicate> filters) {

	
		if (criteriaList.isEmpty()) {
			return null;
		}

		for (SearchCriteria criteria : criteriaList) {
			if (criteria.getOperation().equalsIgnoreCase(">double")) {
				filters.add(builder.greaterThan(root.<Double>get(criteria.getKey()),
						Double.parseDouble(criteria.getValue().toString())));
			} else if (criteria.getOperation().equalsIgnoreCase("<double")) {
				filters.add(builder.lessThanOrEqualTo(root.<Double>get(criteria.getKey()),
						Double.parseDouble(criteria.getValue().toString())));
			}
			if (criteria.getOperation().equalsIgnoreCase(">int")) {
				filters.add(builder.greaterThan(root.<Integer>get(criteria.getKey()),
						Integer.parseInt(criteria.getValue().toString())));
			} else if (criteria.getOperation().equalsIgnoreCase("<int")) {
				filters.add(builder.lessThanOrEqualTo(root.<Integer>get(criteria.getKey()),
						Integer.parseInt(criteria.getValue().toString())));
			}
			if (criteria.getOperation().equalsIgnoreCase(">long")) {
				filters.add(builder.greaterThan(root.<Long>get(criteria.getKey()),
						Long.parseLong(criteria.getValue().toString())));
			} else if (criteria.getOperation().equalsIgnoreCase("<long")) {
				filters.add(builder.lessThanOrEqualTo(root.<Long>get(criteria.getKey()),
						Long.parseLong(criteria.getValue().toString())));
			} else if (criteria.getOperation().equalsIgnoreCase("%")) {
				filters.add(builder.like(builder.lower(root.<String>get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equalsIgnoreCase("=")) {
				filters.add(builder.equal(builder.lower(root.<String>get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase()));
			} else if (criteria.getOperation().equalsIgnoreCase("in")) {
				filters.add(builder.lower(root.<String>get(criteria.getKey()))
						.in(criteria.getValue().toString().toLowerCase()));
			} else if (criteria.getOperation().equalsIgnoreCase("notNull")) {
				filters.add(builder.isNotNull(root.<String>get(criteria.getKey())));
			} else if (criteria.getOperation().equalsIgnoreCase("isNull")) {
				filters.add(builder.isNull(root.<String>get(criteria.getKey())));
			} else if (criteria.getOperation().equalsIgnoreCase(">date>")) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				LocalDate value = LocalDate.parse(criteria.getValue().toString(), formatter);
				LocalDate secondValue = LocalDate.parse(criteria.getSecondValue().toString(), formatter);
				filters.add(builder.between(root.<LocalDate>get(criteria.getKey()), value, secondValue));
			} else if (criteria.getOperation().equalsIgnoreCase(">double>")) {
				filters.add(builder.between(root.<Double>get(criteria.getKey()),
						Double.parseDouble(criteria.getValue().toString()),
						Double.parseDouble(criteria.getSecondValue().toString())));
			} else if (criteria.getOperation().equalsIgnoreCase(">int>")) {
				filters.add(builder.between(root.<Integer>get(criteria.getKey()),
						Integer.parseInt(criteria.getValue().toString()),
						Integer.parseInt(criteria.getSecondValue().toString())));
			} else if (criteria.getOperation().equalsIgnoreCase(">long>")) {
				filters.add(builder.between(root.<Long>get(criteria.getKey()),
						Long.parseLong(criteria.getValue().toString()),
						Long.parseLong(criteria.getSecondValue().toString())));
			} else if (criteria.getOperation().equalsIgnoreCase("greaterThanCount")) {
//				Join<T,Candidate> groupJoin = root.join("likes");
//				filters.add(builder.equal(groupJoin.get("name"),criteria.getValue().toString()));
				filters.add(builder.greaterThan(builder.size(root.get(criteria.getKey())),
						Integer.parseInt(criteria.getValue().toString())));
			}
			else if (criteria.getOperation().equalsIgnoreCase("isActiveJobPost")) {
				filters.add(builder.lessThan(builder.size(root.get(criteria.getKey())),
					root.get(criteria.getValue().toString())));
			}
		}

		return builder.and(filters.toArray(new Predicate[filters.size()]));
	}

}
