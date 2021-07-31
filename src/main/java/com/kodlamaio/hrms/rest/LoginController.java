package com.kodlamaio.hrms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.hrms.entities.Employer;
import com.kodlamaio.hrms.entities.User;
import com.kodlamaio.hrms.model.LoginRequest;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.EmployeeService;
import com.kodlamaio.hrms.service.EmployerService;
import com.kodlamaio.hrms.service.UserService;
import com.kodlamaio.hrms.util.TokenProvider;

@RestController
@RequestMapping("api/login")
@CrossOrigin
public class LoginController {

//request.getRequestURI() just endPoint
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private EmployerService employerService;
	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public Result login(@RequestBody LoginRequest loginRequest) {

		User user = null;
		String token = null;
		Employer employer = null;
		try {
			final Authentication authentication = this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			user = userService.findByEmail(loginRequest.getEmail());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = tokenProvider.genareteToken(authentication, user);

			if (!(employeeService.existsEmployeeById(user.getId()))) {
				if (!user.isAcctive()) {
					return new ErrorResult("Hesabınız Aktif değildir");
				}if(user.getDeleted()=='1') {
					return new ErrorResult("Bu hesap silinmiştir");
				}
				if(user.isLock()) {
					return new ErrorResult("Hesabınız askıya alınmıştır");
				}
				if (employerService.existsEmployerById(user.getId())) {
					employer = this.employerService.findEmployerOtherService(user.getId());
					if (!employer.isManagerConfirm()) {
						return new ErrorResult("Hesabınız incelendikten sonra aktif edilecektir.");
					}
				}
			}

		} catch (Exception e) {
			return new ErrorResult("Email veya şifre hatalı.");
		}

		return new SuccessResult(token);
	}
}
