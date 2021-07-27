package com.kodlamaio.hrms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "verification")
public class Verification extends BaseEntity{
	
	//Token geçerlilik süresi 1 saat olarak tayin edildi.
	@Transient
    public static final int EXPIRATION = 60 * 1000 * 60;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String verificationCode;

	private Long userId;
	
	private Date expiryDate;

}
