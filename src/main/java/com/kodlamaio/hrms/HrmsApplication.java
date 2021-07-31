package com.kodlamaio.hrms;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//import java.time.LocalDate;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import com.kodlamaio.hrms.entities.Ability;
//import com.kodlamaio.hrms.entities.Candidate;
//import com.kodlamaio.hrms.entities.City;
//import com.kodlamaio.hrms.entities.Employer;
//import com.kodlamaio.hrms.entities.JobTitle;
//import com.kodlamaio.hrms.entities.Operation;
//import com.kodlamaio.hrms.entities.Role;
//import com.kodlamaio.hrms.repository.AbilityRepository;
//import com.kodlamaio.hrms.repository.CandidateRepository;
//import com.kodlamaio.hrms.repository.CityRepository;
//import com.kodlamaio.hrms.repository.EmployerRepository;
//import com.kodlamaio.hrms.repository.JobTitleRepository;
//import com.kodlamaio.hrms.repository.OperationRepository;
//import com.kodlamaio.hrms.repository.RoleRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.kodlamaio.hrms.entities.Ability;
import com.kodlamaio.hrms.entities.Candidate;
import com.kodlamaio.hrms.entities.City;
import com.kodlamaio.hrms.entities.Employee;
import com.kodlamaio.hrms.entities.JobTitle;
import com.kodlamaio.hrms.entities.Operation;
import com.kodlamaio.hrms.entities.Role;
import com.kodlamaio.hrms.repository.AbilityRepository;
import com.kodlamaio.hrms.repository.CandidateRepository;
import com.kodlamaio.hrms.repository.CityRepository;
import com.kodlamaio.hrms.repository.EmployeeRepository;
import com.kodlamaio.hrms.repository.JobTitleRepository;
import com.kodlamaio.hrms.repository.OperationRepository;
import com.kodlamaio.hrms.repository.RoleRepository;

@SpringBootApplication

public class HrmsApplication implements CommandLineRunner {
	@Autowired
	private AbilityRepository abilityRepo;
	@Autowired
	private JobTitleRepository jobRepository;
	@Autowired
	private CityRepository cityRepository;
//	@Autowired
//	private CandidateRepository candidateRepository;
	@Autowired
	private BCryptPasswordEncoder encode;
//	
//	@Autowired
//	private EmployerRepository empRepo;
//	
	@Autowired
	private RoleRepository roleRepository;
//	@Autowired
//	private OperationRepository operationRepository;

//	@Autowired
//	private MessageChannelRepository channelRepository;
//	
	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	public RequestMappingHandlerMapping requestMappingHandlerMapping;

	public static void main(String[] args) {
		SpringApplication.run(HrmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Role role = new Role();
		Role role1 = new Role();

		Role role2 = new Role();

		role.setCode("CANDIDATE");
		role.setName("Candidate");
		role.setDescription("Sadece adaylar");
		role1.setCode("EMPLOYER");
		role1.setName("Employer");
		role1.setDescription("Sadece işverenler");
		role2.setCode("ADMIN");
		role2.setName("Admin");
		role2.setDescription("Sadece adminler");

		for (RequestMappingInfo a : requestMappingHandlerMapping.getHandlerMethods().keySet()) {
			String path = a.getPatternsCondition().getPatterns().toArray()[0].toString();
			String code = (a.getMethodsCondition().isEmpty() ? "GET"
					: a.getMethodsCondition().getMethods().toArray()[0].toString());
			if (!path.contains("api")) {
				Operation operation = new Operation();
				operation.setCode(path + "_" + code);
				operation.setMethod(code);
				operation.setPath(path);
				this.operationRepository.save(operation);
				if (!path.contains("admin")) {
					role.getOperations().add(operation);
					role1.getOperations().add(operation);
				}
				role2.getOperations().add(operation);

			}

		}
		this.roleRepository.save(role2);
		this.roleRepository.save(role1);
		this.roleRepository.save(role);
		Employee employee = new Employee();
		employee.setEmail("admin6115");
		employee.setPassword(encode.encode("piral6115"));
		employee.setFirstName("Muhammed");
		employee.setLastName("Piral");
		employee.setRoles(Arrays.asList(role2));
		this.employeeRepository.save(employee);

		String[] name = { "Ahmet", "Mehmet", "Samet", "Sinan", "Ali Rıza", "Hakkı", "Can", "Ramazan", "Arif", "Hakan",
				"Ali", "Recep", "Burhan", "Orhan", "Serdar", "Yavuz", "Mecnun", "Aslı", "Ece", "Hatice", "Filiz",
				"Merve", "Büşra", "Cemile", "Ecem", "Derya", "Deniz", "Kübra" };

		String[] lastName = { "Yılmaz", "Yıldız", "Balçık", "Demir", "Aslan", "Çolak", "Ercankan", "Manap", "Savaş",
				"Aydın", "Solmaz", "Doğam", "Sancak" };

		String[] email = { "@gmail.com", "@hotmail.com", "@outlook.com" };

		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates = Arrays.asList(LocalDate.of(1998, Month.AUGUST, 18), LocalDate.of(1995, Month.FEBRUARY, 23),
				LocalDate.of(2000, Month.MARCH, 2), LocalDate.of(2001, Month.APRIL, 13));
		Random rand = new Random();
		for (int i = 1; i < 100; i++) {
			Candidate bootCandidate = new Candidate();
			int randResulForName = rand.nextInt(27);
			Long tcRandNumber = rand.nextLong();
			bootCandidate.setName(name[randResulForName]);
			bootCandidate.setLastName(lastName[rand.nextInt(12)]);
			bootCandidate
					.setEmail(bootCandidate.getName() + bootCandidate.getLastName() + "_" + i + email[rand.nextInt(2)]);
			bootCandidate.setIdentityNumber(tcRandNumber);
			bootCandidate.setBirtOfDate(dates.get(rand.nextInt(3)));
			bootCandidate.setPassword(encode.encode("piral"));
			bootCandidate.getRoles().add(role);
			bootCandidate.setAcctive(true);
			if (randResulForName < 17)
				bootCandidate.setGender("Erkek");
			else
				bootCandidate.setGender("Kadın");

			this.candidateRepository.save(bootCandidate);
		}
		
		List<String> abilties = Arrays.asList("Spor Yapmak", "Yürüyüş Yapmak / Koşmak", "Yüzmek",
				"Oyun Oynamak", "Bisiklet Sürmek", "Kamp Yapmak", "Balık Tutmak",
				"Bahçeyle Uğraşmak", "Mağaracılık", "Doğa Yürüyüşü (Treking)", "Haber, Dergi veya Makale Okumak",
				"Forumlarda, Sözlüklerde Takılmak", "e-Spor Oyunları Oynamak", "Blog Açmak",
				"Web Sitesi Kurmak", "Bir Şeyler Satmak",
				"Kodlama Öğrenmek", "Dizi – Film İzlemek", "Youtube’da Takılmak",
				" Freelance İş Yapmak", " Yeni Birileriyle Tanışmak","Çocuklarla Oyun Oynamak:","Kart Oyunları Oynamak",
				"Satranç Oynamak:","Çizmek","Yabancı Dil Öğrenmek","Yazmak","Kitap Okumak:","Müzeleri Gezmek","Tarihi Yerleri Gezmek");

	

		List<String> titles = Arrays.asList("Biyomedikal mühendisi", "Veri Bilimci", "Yazılım Mühendisi",
				"Petrol mühendisi", "Bilgisayar Programcısı", "İnşaat Mühendisi", "Makina Mühendisi",
				"Bilgisayar Mühendisi", "Elektrik Elktronik Mühendisi", "Mekatronik Mühendisi", "Çevre Mühendisi",
				"Ziraat Mühendisi", "Yazılım Geliştirme Uzmanlığı", "Web Geliştirme Uzmanı",
				"PMP Proje Yöneticisi (Project Management Professional)", "iOS ve Android Mobil Yazılım Uzmanlıkları",
				"Oracle Veritabanı Uzmanlığı (DBA)", "Kurumsal Mimari Uzmanlığı - TOGAF", "Teknik Destek Uzmanlığı",
				"İşletim Sistemi Yöneticiliği", "Sistem, Ağ ve Güvenlik Uzmanlığı","Yemek Yapmak","El Sanatları"," Şarkı Söylemek"
				,"Dans Etmek","Halk Oyunları","Fotoğrafçılık");

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
		abilties.forEach(item -> {
			Ability ability = new Ability();
			ability.setAbilityName(item);
			this.abilityRepo.save(ability);

		});

	}

}
