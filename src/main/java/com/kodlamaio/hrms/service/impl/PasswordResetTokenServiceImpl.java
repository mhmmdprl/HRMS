package com.kodlamaio.hrms.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kodlamaio.hrms.entities.PasswordResetToken;
import com.kodlamaio.hrms.entities.User;
import com.kodlamaio.hrms.model.ResetPasswordRequest;
import com.kodlamaio.hrms.repository.PasswordResetTokenRepository;
import com.kodlamaio.hrms.result.ErrorResult;
import com.kodlamaio.hrms.result.Result;
import com.kodlamaio.hrms.result.SuccessResult;
import com.kodlamaio.hrms.service.EmailService;
import com.kodlamaio.hrms.service.PasswordResetTokenService;
import com.kodlamaio.hrms.service.UserService;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Override
	public Result create(String email) {
		User user = null;
		PasswordResetToken passwordResetToken = null;
		PasswordResetToken isExistPasswordResetToken = null;
		String token = null;
		String url = "http://mpiral.com/auth/changePassword/";

		try {
			if (this.userService.existsByEmailAndDeleted(email)) {
				user = this.userService.findByEmail(email);
				isExistPasswordResetToken = this.passwordResetTokenRepository.findByUser_Id(user);
				if (isExistPasswordResetToken != null) {
					isExistPasswordResetToken.setDeleted('1');
					this.passwordResetTokenRepository.save(isExistPasswordResetToken);
				}
				passwordResetToken = new PasswordResetToken();
				token = UUID.randomUUID().toString();
				passwordResetToken.setToken(token);
				passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + PasswordResetToken.EXPIRATION));
				passwordResetToken.setUser(user);
				this.passwordResetTokenRepository.save(passwordResetToken);
				this.emailService.sendSimpleMessage(user.getEmail(), "Şifre Sıfırlama",
						"Şifre sıfırlamak için linke tıklayınız : " + url + token);
			} else {
				return new ErrorResult("Email hatalı.");
			}
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}
		return new SuccessResult(
				"Mail adresinize link gönderildi.Lütfen linke tıklayıp sıfırlama işlemine devam ediniz.");
	}

	@Override
	public Result tokenControl(String token) {
		PasswordResetToken passwordResetToken = null;
		try {
			if (this.passwordResetTokenRepository.existsByTokenAndDeleted(token, '0')) {
				passwordResetToken = this.passwordResetTokenRepository.findByTokenAndDeleted(token, '0');
				if (this.validatePasswordResetToken(passwordResetToken)) {
					return new SuccessResult("Token aktif");
				} else {
					return new ErrorResult("Token geçerlilik süresi aktif değik.");
				}
			} else {
				return new ErrorResult("Geçersiz token");
			}
		} catch (Exception e) {
			return new ErrorResult("Hata : " + e.getMessage());
		}

	}

	@Override
	public boolean validatePasswordResetToken(PasswordResetToken passwordResetToken) {
		if (passwordResetToken != null) {

			Calendar cal = Calendar.getInstance();
			return !passwordResetToken.getExpiryDate().before(cal.getTime());
		}
		return false;
	}

	@Override
	public Result resetPassword(ResetPasswordRequest resetPasswordRequest) {
		PasswordResetToken passwordResetToken=null;
		User user=null;
		try {
			if(resetPasswordRequest.getPassword().equals(resetPasswordRequest.getRePassword())) {
				passwordResetToken=this.passwordResetTokenRepository.findByTokenAndDeleted(resetPasswordRequest.getToken(), '0');
				user=this.userService.findByEmail(passwordResetToken.getUser().getEmail());
				user.setPassword(this.passwordEncode.encode(resetPasswordRequest.getPassword()));
				this.userService.save(user);
				passwordResetToken.setDeleted('1');
				this.passwordResetTokenRepository.save(passwordResetToken);
			}
			else {
				
				return new ErrorResult("Şifreler Eşleşmiyor");
			}
		} catch (Exception e) {
			return new ErrorResult("Hata : "+e.getMessage());
		}
		
		return new SuccessResult("Şifre Sıfırlandı");
	}

}
