package com.kodlamaio.hrms.entities;

import java.util.Date;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Column(name = "DELETED")
	private Character deleted = '0';

	@Column(name = "UUID", nullable = false, updatable = false, unique = true)
	private String uuid = UUID.randomUUID().toString();

	@CreatedDate
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private Date createdDate;

	@CreatedBy
	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy = 1L;

	@LastModifiedDate
	@Column(name = "UPDATED_DATE", insertable = false)
	private Date updatedDate;

	@LastModifiedBy
	@Column(name = "UPDATED_BY", insertable = false)
	private Long updatedBy;

}
