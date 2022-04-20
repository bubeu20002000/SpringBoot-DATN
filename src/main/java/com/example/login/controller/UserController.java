package com.example.login.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.User;
import com.example.login.repo.UserRepo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")

public class UserController {
	@Autowired
	UserRepo userRepo;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
	    Optional<User> UserData = userRepo.findById(id);
	    if (UserData.isPresent()) {
	      return new ResponseEntity<>(UserData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	  public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User User) {
	    Optional<User> UserData = userRepo.findById(id);
	    if (UserData.isPresent()) {
	      User _User = UserData.get();
	      _User.setAddress_1(User.getAddress_1());
	      _User.setCity(User.getCity());
	      _User.setDistrict(User.getDistrict());
	      _User.setWard(User.getWard());
	      return new ResponseEntity<>(userRepo.save(_User), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	@GetMapping("/test")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String hello() {
		return "Fk";
	}

}
