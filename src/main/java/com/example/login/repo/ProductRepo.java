package com.example.login.repo;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.login.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
	Page<Product> findByProdtypeContainingOrProdnameContaining(String type,String name, Pageable pageable);
	Page<Product> findByProdstatus(boolean status, Pageable pageable);
	Page<Product> findByProdnameContaining(String name,Pageable  paging);
	List<Product> findByProdnameContaining(String title, Sort sort);
}
