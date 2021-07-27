package com.kodlamaio.hrms.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerUpdateRequest {

	@NotBlank(message = "Şirket ismi giriniz")
	private String companyName;
	@NotBlank(message = "Web adresinizi giriniz")
	private String webAddress;
	@NotBlank(message = "Telefon numaranızı giriniz")
	@Pattern(regexp="^(\\d{3}[- .]?){2}\\d{4}$",message = "Telefon numaranızı başında 0 olmadan 10 haneli olarak giriniz")
	private String phoneNumber;
}
