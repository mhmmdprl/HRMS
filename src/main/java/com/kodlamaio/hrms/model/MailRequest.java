package com.kodlamaio.hrms.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {

	@NotBlank(message = "Mail adresi boş bırakılamaz (Gönderen)")
	private String from;
	@NotBlank(message = "Mail adresi boş bırakılamaz (Alıcı)")
	private String to;
	
	private String text;
	@NotBlank(message = "Başlık Giriniz")
	private String subject;
	
	
}
