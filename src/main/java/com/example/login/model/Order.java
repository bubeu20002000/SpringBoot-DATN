package com.example.login.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String company;
	
	@Column(nullable = false)
	private String address1;
	
	private String address2;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String district;
	
	@Column(nullable = false)
	private String ward;
	
	private String zipcode;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String date;
	
	private String note;
	
	private double total;
	
	@Column(nullable = false)
	private int status;
	
	@Column(nullable = false, name = "payment_method")
	private int paymentmethod;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinColumn(name = "cart_id")
	private List<Cart> cart;

	public Order(String name, String company, String address1, String address2, String city, String district,
			String ward, String zipcode, String phone, String email, String date, String note, double total, int status,
			int paymentmethod, User user, List<Cart> cart) {
		super();
		this.name = name;
		this.company = company;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.district = district;
		this.ward = ward;
		this.zipcode = zipcode;
		this.phone = phone;
		this.email = email;
		this.date = date;
		this.note = note;
		this.total = total;
		this.status = status;
		this.paymentmethod = paymentmethod;
		this.user = user;
		this.cart = cart;
	}

	
	
	
	
}
