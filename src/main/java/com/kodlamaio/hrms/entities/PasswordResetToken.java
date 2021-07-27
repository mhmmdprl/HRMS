package com.kodlamaio.hrms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PasswordResetToken extends BaseEntity {
	
	@Transient
    public static final int EXPIRATION = 60 * 1000 * 30;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private String token;
	@ManyToOne(targetEntity = User.class)
	private User user;

    private Date expiryDate;
}
