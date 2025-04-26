package com.example.demo.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

	//List
	public Page<User> findAll(Pageable pageable); 
		
	//ID check
	boolean existsById(String id);
	
	//Serach
	public Page<User> findByNameContaining(String keyword,Pageable pageable);

	
}
