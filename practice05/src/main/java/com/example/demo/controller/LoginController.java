package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/cookie")
public class LoginController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value={"","/"})
	public String main(@CookieValue(name="userId",required=false)String userId,Model model) {
		model.addAttribute("loginType","cookie");
		model.addAttribute("pageTitle","Cookie Login");
		model.addAttribute("userId",userId);
		System.out.println(userId);
		//getLoginUserById
		User loginUser=userService.getLoginUserById(userId);
		if(loginUser!=null) {
			model.addAttribute("userName",loginUser.getName());
			
		}
		return "template/login/main";
	} 
	
}
