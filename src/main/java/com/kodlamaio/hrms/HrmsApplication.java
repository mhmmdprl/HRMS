package com.kodlamaio.hrms;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kodlamaio.hrms.entities.Ability;
import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.City;
import com.kodlamaio.hrms.entities.JobTitle;
import com.kodlamaio.hrms.repository.AbilityRepository;
import com.kodlamaio.hrms.repository.CandidateRepository;
import com.kodlamaio.hrms.repository.CityRepository;
import com.kodlamaio.hrms.repository.JobTitleRepository;

@SpringBootApplication
public class HrmsApplication implements CommandLineRunner {
	@Autowired
	private AbilityRepository abilityRepo;
	@Autowired
	private JobTitleRepository jobRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CandidateRepository candidateRepository;

	public static void main(String[] args) {
		SpringApplication.run(HrmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<String> titles = Arrays.asList("Software Engineer", "Computer Engineer", "Machine Engineer");

		List<String> cities = Arrays.asList("Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya",
				"Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale",
				"Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum ", "Eskişehir",
				"Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir",
				"Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya ", "Malatya",
				"Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya",
				"Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon  ", "Tunceli", "Şanlıurfa", "Uşak",
				"Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt ", "Karaman", "Kırıkkale", "Batman", "Şırnak",
				"Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük ", "Kilis", "Osmaniye ", "Düzce");
		cities.stream().forEach(item -> {
			City city = new City();
			city.setCityName(item);
			this.cityRepository.save(city);
		});

		titles.forEach(item -> {
			JobTitle jobTitle = new JobTitle();
			jobTitle.setTitle(item);
			this.jobRepository.save(jobTitle);

		});

		Ability ability = new Ability();
		ability.setAbilityName("Java");
		this.abilityRepo.save(ability);
		Candidate candidate=new Candidate();
		candidate.setEmail("m@m.com");
		candidate.setName("muhammed");
		candidate.setLastName("piral");
		this.candidateRepository.save(candidate);

	}

}
