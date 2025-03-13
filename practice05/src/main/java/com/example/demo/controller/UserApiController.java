package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserCreateDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;
	
	@PostMapping("/api/user")
	public ResponseEntity<User> userCreate(@RequestBody UserCreateDTO userCreateDTO) {
		User savedUser = userService.save(userCreateDTO);	
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedUser);
	}
	

}
