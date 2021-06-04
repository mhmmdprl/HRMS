package com.kodlamaio.hrms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "verification")
@Where(clause = "deleted='0'")
public class Verification extends BaseEntity{
	
	//Token geçerlilik süresi 1 saat olarak tayin edildi.
	@Transient
    public static final int EXPIRATION = 60 * 1000 * 1;
	
	private String verificationCode;

	private Long userId;
	
	private Date expiryDate;

}
