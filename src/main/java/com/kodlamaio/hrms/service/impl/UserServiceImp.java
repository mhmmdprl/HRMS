package com.kodlamaio.hrms.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.User;
import com.kodlamaio.hrms.model.ChangePaswordRequest;
import com.kodlamaio.hrms.repository.UserRepository;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.UserService;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncode;

	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Geçersiz kullanıcı adı ve ya şifre");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				this.getAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return authorities;
	}

	@Override
	public boolean existsByEmailAndDeleted(String email) {
		return this.userRepository.existsByEmailAndDeleted(email, '0');
	}

	@Override
	public void save(User user) {
		this.userRepository.save(user);
	}

	@Override
	public Result changePassword(ChangePaswordRequest changePasswordRequest, Long userId) {
		User user = null;
		try {
			user = this.userRepository.findById(userId).get();
			if (this.bcryptEncode.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {

				if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getReNewPassword())) {
					user.setPassword(this.bcryptEncode.encode(changePasswordRequest.getReNewPassword()));
					this.userRepository.save(user);

				} else {
					return new ErrorResult("Şifreler eşleşmiyor");
				}
			} else {
				return new ErrorResult("Şifre hatalı");
			}
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult("Şifre değiştirildi");
	}

	@Override
	public User findById(Long id) {
		return this.userRepository.findById(id).get();
	}

}
