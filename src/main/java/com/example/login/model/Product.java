package com.example.login.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String sku;
	@Column(name = "prod_name", unique = true, nullable = false)
	private String prodname;
	@Column(name = "prod_type", nullable = false)
	private String prodtype;
	@Column(name = "prod_size", nullable = false)
	private String prodsize;
	@Column(name = "prod_description")
	private String proddescription;
	@Column(name = "prod_instock", nullable = false)
	private int prodinstock;
	@Column(name = "prod_price", nullable = false)
	private double prodprice;
	@Column(name = "prod_status", nullable = false)
	private Boolean prodstatus;
	
	@ManyToOne
	@JoinColumn(name = "cat_id", referencedColumnName = "id")
	private Category category;

	public Product() {
		super();
	}

	public Product(String sku, String prodname, String prodtype, String prodsize, String proddescription,
			int prodinstock, double prodprice, Boolean prodstatus, Category category) {
		super();
		this.sku = sku;
		this.prodname = prodname;
		this.prodtype = prodtype;
		this.prodsize = prodsize;
		this.proddescription = proddescription;
		this.prodinstock = prodinstock;
		this.prodprice = prodprice;
		this.prodstatus = prodstatus;
		this.category = category;
	}
	
	
}

