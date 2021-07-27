package com.kodlamaio.hrms.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class JobPostingUpdateRequest {

	private Long id;
	
	private double maxSalary;

	private double minSalary;
	private boolean cvMandatory;
	@NotNull
	@NotBlank(message = "İlanınızı özetleyiniz...")
	@Size(min=50,max=255,message = "İlanı Özeti 50 ile 255 karakter arasında olmalıdır!")
	private String postSummary;

	@NotNull
	private int numberOfAvailablePosition;

	@Future(message = "Son başvuru tarihi bugünden önce olamaz")
	private Date applicationDeadline;

	private List<String> criteria;
	@NotNull
	@NotBlank(message = "Pozisyon Seçmelisiniz")
	private String jobTitleName;

	@NotNull
	@NotBlank(message = "Şehir seçmelisiniz")
	private String cityName;

}
