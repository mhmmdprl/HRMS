package com.kodlamaio.hrms.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

	
	@Column(name = "DELETED")
	private Character deleted = '0';
	
	@Column(name = "UUID", nullable = false,updatable = false,unique = true)
	private String uuid=UUID.randomUUID().toString();

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false,updatable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY",nullable = false, updatable = false)
	private Long createdBy = 1L;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", insertable = false)
	private Date updatedDate;

	@Column(name = "UPDATED_BY", insertable = false)
	private Long updatedBy;
	

}
