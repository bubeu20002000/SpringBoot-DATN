package com.example.login.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Cart;
import com.example.login.model.Order;
import com.example.login.model.User;
import com.example.login.repo.CartRepo;
import com.example.login.repo.OrderRepo;
import com.example.login.repo.UserRepo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OrderRepo orderRepo;

	@PostMapping("/add/{id}")
	public ResponseEntity<Order> addOrder(@PathVariable("id") Long id, @RequestBody Order order) {
		try {
			List<Cart> _cart = cartRepo.findByUser_Id(id);
			Optional<User> user = userRepo.findById(id);
			User _user = user.get();
			Order _order = orderRepo.save(new Order(order.getName(), order.getCompany(), order.getAddress1(),
					order.getAddress2(), order.getCity(), order.getDistrict(), order.getWard(), order.getZipcode(),
					order.getPhone(), order.getEmail(), order.getDate(), order.getNote(), order.getTotal(),
					order.getStatus(),order.getPaymentmethod(),_user, _cart));

			return new ResponseEntity<>(orderRepo.save(_order), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
