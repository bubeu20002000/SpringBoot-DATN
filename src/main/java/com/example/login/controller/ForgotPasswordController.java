package com.example.login.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.User;
import com.example.login.repo.UserRepo;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")

public class ForgotPasswordController {
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/send/{email}")
	public void send(@PathVariable("email") String email) {
		String token = RandomString.make(6);
		Optional<User> user = userRepo.findByEmail(email);
		try {
			if (user.isPresent()) {
				User _User = user.get();
				String resetPasswordToken = token;
				_User.setPasstoken(token);
				sendEmail(email, resetPasswordToken);
				userRepo.save(_User);
			}
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	@PutMapping("/update-p/{token}")
	public ResponseEntity<User> updatePUser(@PathVariable("token") String token, @RequestBody String password) {
		Optional<User> UserData = userRepo.findByPasstoken(token);
		if (UserData.isPresent()) {
			User _User = UserData.get();
			_User.setPassword(encoder.encode(password));
			_User.setPasstoken(null);
			return new ResponseEntity<>(userRepo.save(_User), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public void sendEmail(String recipientEmail, String link) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shopme.com", "Shopme Support");
		helper.setTo(recipientEmail);

		String subject = "Qu??n m???t kh???u";

		String content = "<p>Xin ch??o,</p>" + "<p>B???n ???? y??u c???u c???p m???t kh???u m???i.</p>"
				+ "<p>B??n d?????i l?? code ????? thay ?????i m???t kh???u c???a b???n:</p>" + "<h1><strong>"+link+"</strong></h1>" + "<br>" + "<p>B??? qua th?? n??y n???u b???n ???? nh??? m???t kh???u, "
				+ "ho???c b???n kh??ng y??u c???u thay ?????i m???t kh???u.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}
}
