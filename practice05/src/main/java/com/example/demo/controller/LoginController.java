package com.example.demo.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
	//Login
	@GetMapping("/login")
	public String LoginPage(Model model) {
		model.addAttribute("loginType","cookie");
		model.addAttribute("pageTitle","Cookie Login");
		model.addAttribute("loginRequest", new LoginRequest());
		return "template/login/login";
	}
	//Login(POST)
	@PostMapping("/login")
	public String login(
			@ModelAttribute LoginRequest loginRequest,
			BindingResult bindingResult,
			HttpServletResponse response,
			Model model) {
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "Cookie Login");
		
		User user =userService.login(loginRequest);
		//로그인 정보 불일치시
		if(user==null) {
			bindingResult.reject("loginFail", "입력하신 아이디 또는 비밀번호가 틀렸습니다.");
		}
		
		//로그인 실패시 에러메시지 출력
		if(bindingResult.hasErrors()) {
			return "template/login/login";
		}
		
		//로그인 성공시 쿠키생성
		Cookie cookie=new Cookie("userId",String.valueOf(user.getId()));
		cookie.setMaxAge(120); //쿠키 만료시간(초단위) 설정 
		cookie.setPath("/");//쿠키경로 전체설정
		
		response.addCookie(cookie);
		return "redirect:/cookie";
	}
	
	//Logout(GET)
	@GetMapping("/logout")
	public String logout(HttpServletResponse res,Model model) {
		model.addAttribute("loginType","cookie");
		model.addAttribute("pageTitle","Cookie Logout");
		
		//제거 Cookie
		Cookie cookie =new Cookie("userId",null);
		cookie.setMaxAge(0);
		res.addCookie(cookie);
		
		return "redirect:/cookie";
	}
	//My page
	@GetMapping("/mypage")
	public String myPage(@CookieValue(name="userId",required=false)String userId,Model model) {
		model.addAttribute("loginType","cookie");
		model.addAttribute("pageTitle","My Page");
		
		User loginUser= userService.getLoginUserById(userId);
		
		if(loginUser==null) {
			return "redirect:/cookie/login";
		}
		
		model.addAttribute("user",loginUser);
		return "template/login/mypage";
	}
	//update
	@GetMapping("/update")
	public String update(@CookieValue(name="userId", required=false) String userId, Model model) {
		
		User loginUser = userService.getLoginUserById(userId);
		System.out.println("login user: "+ loginUser);
		if(loginUser==null) { //로그인 유저가 없으면
			return "redirect:/cookie/login";
		}
		// 해당 수정페이지로 이동
		return "redirect:/user/userUpdate.do/"+loginUser.getIdx();
	}
}
