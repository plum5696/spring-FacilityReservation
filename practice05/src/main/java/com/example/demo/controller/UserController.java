package com.example.demo.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	UserService userService; //사용자 서비스 객체
	
	//사용자 등록 페이지
	@GetMapping("/user/userCreate.do")
	public String userInsertUi(UserCreateDTO userCreateDTO) {
		return "template/user/userCreate.html";
	}
	
	//사용자 등록처리	
	@PostMapping("/user/userCreate.do")
	public String userInsert(UserCreateDTO userCreateDTO) {
		userService.save(userCreateDTO);
		return "redirect:/user/userList.do";
	}
	
	//사용자 목록 페이지
	@GetMapping("/user/userList.do")
	public String userList(Model model,@RequestParam(value="page",defaultValue="0") int page ) {
		
		Page<User> userList = this.userService.userList(page);
		model.addAttribute("userList", userList); //model 객체로 userList 전달
		
		return "template/user/userList.html";
	}
	
	//사용자정보 수정 페이지
	@GetMapping("/user/userUpdate.do/{idx}")
	public ModelAndView userUpdateUi(@PathVariable("idx") Integer idx){
		ModelAndView mav = new ModelAndView();
		try {
			UserUpdateDTO userUpdateDTO = this.userService.updateUi(idx);
			mav.addObject("userUpdateDTO",userUpdateDTO);
			mav.setViewName("template/user/userUpdate.html");
			
		}catch(NoSuchElementException ex) {
			ex.printStackTrace();
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.setViewName("template/user/userList.html");
		}
		return mav;
	}
	
	//사용자 삭제처리
	@GetMapping("/user/userDelete.do/{idx}")
	public String userDelete(@PathVariable("idx") Integer idx) {
		this.userService.delete(idx); //사용자 삭제
		
		return "redirect:/user/userList.do";
	}
	
	//사용자 수정 처리
	@PostMapping("/user/userUpdate.do/{idx}")
	public String UserUpdate(UserUpdateDTO userUpdateDTO) {		
		this.userService.update(userUpdateDTO);
		return "redirect:/user/userList.do";
	}
	
	//예외처리
	@ExceptionHandler(UserException.class)
	public ModelAndView handleError(HttpServletRequest req, UserException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception",exception);
		mav.addObject("url",req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("errorUser");
		return mav;
	}
	

	
}
