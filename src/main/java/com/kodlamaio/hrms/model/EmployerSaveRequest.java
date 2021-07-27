package com.kodlamaio.hrms.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerSaveRequest {

	@Email(message = "email formatına uygun girmelisiniz")
	@NotBlank(message = "Email giriniz")
	private String email;
	@Size(min = 6,max = 14,message = "6 ile 14 karakter arasında şifre belirleyiniz")
	@NotBlank(message = "Şifrenizi giriniz")
	private String password;
	@NotBlank(message = "Şirket ismi giriniz")
	private String companyName;
	@NotBlank(message = "Web adresinizi giriniz")
	private String webAddress;
	@NotBlank(message = "Telefon numaranızı giriniz")
	@Pattern(regexp="^(\\d{3}[- .]?){2}\\d{4}$",message = "Telefon numaranızı başında 0 olmadan 10 haneli olarak giriniz")
	private String phoneNumber;
	
	@NotBlank(message="Hakımızda Kısmını Boş bırakmayınız")
	@Size(max = 255,message = "Hakkımızda kısmı enfazla 255 karakter içermelidir")
	private String aboutCompany;
	
}
