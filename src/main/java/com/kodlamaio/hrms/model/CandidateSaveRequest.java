package com.kodlamaio.hrms.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateSaveRequest {

	@NotBlank(message = "İsim boş bırakılamaz")
	private String name;
	
	@NotBlank(message = "Soy isim boş bırakılamaz")
	private String lastName;
	/*@NotNull
	private Long identityNumber;*/
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birtOfDate;
	@Email(message = "email formatına uygun girmelisiniz")
	private String email;
	@Size(min = 6,max = 14,message = "6 ile 14 karakter arasında şifre belirleyiniz")
	@NotBlank(message = "Şifre giriniz!")
	private String password;
	@NotBlank(message = "Cinsiyet seçiniz!")
	private String gender;
	
}
