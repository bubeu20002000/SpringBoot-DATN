package com.example.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Product;
import com.example.login.repo.ProductRepo;
import com.example.login.specification.staticmetamodel.ProductRequest;
import com.example.login.specification.staticmetamodel.ProductSpectification;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductSpectification productSpectification;

//	@GetMapping("/products")
//	public ResponseEntity<Map<String, Object>> getAllProducts(@RequestParam(required = false) String title,
//			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
//		try {
//			List<Product> products = new ArrayList<Product>();
//			Pageable paging = PageRequest.of(page, size);
//
//			Page<Product> pageProds;
//			if (title == null)
//				pageProds = productRepo.findAll(paging);
//			else
//				pageProds = productRepo.findByProdnameContaining(title, paging);
//			products = pageProds.getContent();
//			Map<String, Object> response = new HashMap<>();
//			response.put("products", products);
//			response.put("currentPage", pageProds.getNumber());
//			response.put("totalItems", pageProds.getTotalElements());
//			response.put("totalPages", pageProds.getTotalPages());
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	@GetMapping("/products-info")
	public List<Product> getInfo(){
		return productRepo.findAll();
	}

	@PostMapping("/products/{page}/{size}")
	public ResponseEntity<Map<String, Object>> test2(@Valid @RequestBody(required = false) ProductRequest productRequest,
			@PathVariable("page") int page, @PathVariable("size") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Product> pageProds;
		List<Product> products = new ArrayList<Product>();
		if (productRequest == null) {
			pageProds = productRepo.findAll(paging);
		} else {
			pageProds = productRepo.findAll(productSpectification.getProds(productRequest), paging);
		}
		products = pageProds.getContent();
		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("products", products);
		response.put("currentPage", pageProds.getNumber());
		response.put("totalItems", pageProds.getTotalElements());
		response.put("totalPages", pageProds.getTotalPages());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
