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
		role1.setDescription("Sadece i??verenler");
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

		String[] name = { "Ahmet", "Mehmet", "Samet", "Sinan", "Ali R??za", "Hakk??", "Can", "Ramazan", "Arif", "Hakan",
				"Ali", "Recep", "Burhan", "Orhan", "Serdar", "Yavuz", "Mecnun", "Asl??", "Ece", "Hatice", "Filiz",
				"Merve", "B????ra", "Cemile", "Ecem", "Derya", "Deniz", "K??bra" };

		String[] lastName = { "Y??lmaz", "Y??ld??z", "Bal????k", "Demir", "Aslan", "??olak", "Ercankan", "Manap", "Sava??",
				"Ayd??n", "Solmaz", "Do??am", "Sancak" };

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
				bootCandidate.setGender("Kad??n");

			this.candidateRepository.save(bootCandidate);
		}
		
		List<String> abilties = Arrays.asList("Spor Yapmak", "Y??r??y???? Yapmak / Ko??mak", "Y??zmek",
				"Oyun Oynamak", "Bisiklet S??rmek", "Kamp Yapmak", "Bal??k Tutmak",
				"Bah??eyle U??ra??mak", "Ma??arac??l??k", "Do??a Y??r??y?????? (Treking)", "Haber, Dergi veya Makale Okumak",
				"Forumlarda, S??zl??klerde Tak??lmak", "e-Spor Oyunlar?? Oynamak", "Blog A??mak",
				"Web Sitesi Kurmak", "Bir ??eyler Satmak",
				"Kodlama ????renmek", "Dizi ??? Film ??zlemek", "Youtube???da Tak??lmak",
				" Freelance ???? Yapmak", " Yeni Birileriyle Tan????mak","??ocuklarla Oyun Oynamak:","Kart Oyunlar?? Oynamak",
				"Satran?? Oynamak:","??izmek","Yabanc?? Dil ????renmek","Yazmak","Kitap Okumak:","M??zeleri Gezmek","Tarihi Yerleri Gezmek");

	

		List<String> titles = Arrays.asList("Biyomedikal m??hendisi", "Veri Bilimci", "Yaz??l??m M??hendisi",
				"Petrol m??hendisi", "Bilgisayar Programc??s??", "??n??aat M??hendisi", "Makina M??hendisi",
				"Bilgisayar M??hendisi", "Elektrik Elktronik M??hendisi", "Mekatronik M??hendisi", "??evre M??hendisi",
				"Ziraat M??hendisi", "Yaz??l??m Geli??tirme Uzmanl??????", "Web Geli??tirme Uzman??",
				"PMP Proje Y??neticisi (Project Management Professional)", "iOS ve Android Mobil Yaz??l??m Uzmanl??klar??",
				"Oracle Veritaban?? Uzmanl?????? (DBA)", "Kurumsal Mimari Uzmanl?????? - TOGAF", "Teknik Destek Uzmanl??????",
				"????letim Sistemi Y??neticili??i", "Sistem, A?? ve G??venlik Uzmanl??????","Yemek Yapmak","El Sanatlar??"," ??ark?? S??ylemek"
				,"Dans Etmek","Halk Oyunlar??","Foto??raf????l??k");

		List<String> cities = Arrays.asList("Adana", "Ad??yaman", "Afyon", "A??r??", "Amasya", "Ankara", "Antalya",
				"Artvin", "Ayd??n", "Bal??kesir", "Bilecik", "Bing??l", "Bitlis", "Bolu", "Burdur", "Bursa", "??anakkale",
				"??ank??r??", "??orum", "Denizli", "Diyarbak??r", "Edirne", "Elaz????", "Erzincan", "Erzurum ", "Eski??ehir",
				"Gaziantep", "Giresun", "G??m????hane", "Hakkari", "Hatay", "Isparta", "Mersin", "??stanbul", "??zmir",
				"Kars", "Kastamonu", "Kayseri", "K??rklareli", "K??r??ehir", "Kocaeli", "Konya", "K??tahya ", "Malatya",
				"Manisa", "Kahramanmara??", "Mardin", "Mu??la", "Mu??", "Nev??ehir", "Ni??de", "Ordu", "Rize", "Sakarya",
				"Samsun", "Siirt", "Sinop", "Sivas", "Tekirda??", "Tokat", "Trabzon  ", "Tunceli", "??anl??urfa", "U??ak",
				"Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt ", "Karaman", "K??r??kkale", "Batman", "????rnak",
				"Bart??n", "Ardahan", "I??d??r", "Yalova", "Karab??k ", "Kilis", "Osmaniye ", "D??zce");
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
