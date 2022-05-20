package com.example.login.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.login.model.Cart;
@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{
	List<Cart> findByUser_Id(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from shop.cart c where c.product_id = ?1", nativeQuery = true)
	void deleteByProductId(Long id);
}
