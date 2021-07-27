package com.kodlamaio.hrms.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateUpdateRequest {

	
	@NotBlank(message = "İsim boş bırakılamaz")
	private String name;
	
	@NotBlank(message = "Soy isim boş bırakılamaz")
	private String lastName;
	@NotNull
	private Long identityNumber;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birtOfDate;

}
