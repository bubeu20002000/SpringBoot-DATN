package com.example.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Product;
import com.example.login.repo.ProductRepo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductRepo productRepo;
	
	 private Sort.Direction getSortDirection(String direction) {
		    if (direction.equals("asc")) {
		      return Sort.Direction.ASC;
		    } else if (direction.equals("desc")) {
		      return Sort.Direction.DESC;
		    }
			return Sort.Direction.ASC;
		}

	@GetMapping("/products")
	public ResponseEntity<Map<String, Object>> getAllProducts(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);

			Page<Product> pageProds;
			if (title == null)
				pageProds = productRepo.findAll(paging);
			else
				pageProds = productRepo.findByProdnameContaining(title, paging);
			products = pageProds.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProds.getNumber());
			response.put("totalItems", pageProds.getTotalElements());
			response.put("totalPages", pageProds.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/products-sort-price-desc")
	public ResponseEntity<Map<String, Object>> getAllProductsSorted(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size,
			@RequestParam(defaultValue = "prodprice,desc") String[] sort) {
		try {

			List<Order> orders = new ArrayList<Order>();
			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}
			List<Product> products = new ArrayList<Product>();
			
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Product> pageProds;
			if (title == null)
				pageProds = productRepo.findAll(pagingSort);
			else
				pageProds = productRepo.findByProdnameContaining(title, pagingSort);
			products = pageProds.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProds.getNumber());
			response.put("totalItems", pageProds.getTotalElements());
			response.put("totalPages", pageProds.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
