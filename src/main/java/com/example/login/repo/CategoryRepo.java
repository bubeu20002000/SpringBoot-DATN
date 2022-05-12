package com.example.login.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.login.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

}
