package com.kodlamaio.hrms.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePaswordRequest {

	@NotBlank(message = "Şuan ki şifrenizi giriniz")
	private String currentPassword;
	@NotBlank(message = "Yeni şifrenizi giriniz")
	@Size(max =12, min = 6,message = "Şifreniz 6 ile 12 karakter arasında olmalıdır" )
	private String newPassword;
	@NotBlank(message = "Şifrenizi tekrar giriniz")
	@Size(max =12, min = 6,message = "Şifreniz 6 ile 12 karakter arasında olmalıdır" )
	private String reNewPassword;
}
