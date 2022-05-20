package com.example.login.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Cart;
import com.example.login.model.Product;
import com.example.login.model.User;
import com.example.login.repo.CartRepo;
import com.example.login.repo.ProductRepo;
import com.example.login.repo.UserRepo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/add/{sku}/{size}/{id}/{qty}")
	public ResponseEntity<Cart> addtoCart(@PathVariable("sku") String sku, @PathVariable("size") String size,
			@PathVariable("id") Long id, @PathVariable("qty") int qty) {
			Optional<Product> product = productRepo.findBySkuAndProdsize(sku, size);
			Product _Product = product.get();
			if(_Product.getProdinstock()>0) {
				_Product.setProdinstock(_Product.getProdinstock() - qty);
				if (_Product.getProdinstock() <= 0) {
					_Product.setProdstatus(false);
					_Product.setProdinstock(0);
				}
				productRepo.save(_Product);
				Optional<User> user = userRepo.findById(id);
				User _User = user.get();
				Cart _cart = cartRepo.save(new Cart(_User, _Product, qty));
				return new ResponseEntity<>(_cart, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
	}
		
	@GetMapping("/get/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id){
		List<Cart> carts = new ArrayList<>();
		carts = cartRepo.findByUser_Id(id);
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}
	
	@DeleteMapping("/del/{id}")
	  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
	    try {
	      cartRepo.deleteByProductId(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
}
