package com.example.demo.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService; //사용자 서비스 객체
	
	//사용자 등록 페이지
	@GetMapping("/userCreate.do")
	public String userInsertUi(UserCreateDTO userCreateDTO) {
		return "template/user/userCreate.html";
	}
	
	//사용자 등록처리	
	@PostMapping("/userCreate.do")
	public String userInsert(UserCreateDTO userCreateDTO,Model model) {
		//ID중복체크
		boolean IdCheckRes=userService.checkIdDuplication(userCreateDTO.getId());
		System.out.println(IdCheckRes);
		if(IdCheckRes==true) {
			model.addAttribute("msg","ID 중복 에러: 입력하신 ID는 이미 존재합니다.");
			return "template/layout/layout_error";
		}
		userService.save(userCreateDTO); //DB에 저장
		return "redirect:/user/userList.do";
	}
	
	//사용자 목록 페이지
	@GetMapping(value={"/","","/userList.do"})
	public String userList(Model model,@RequestParam(value="page",defaultValue="0") int page,
			@CookieValue(name="userId",required=false)String userId) {
		
		//로그인 여부확인
		User loginUser= userService.getLoginUserById(userId);
		System.out.println("userId: "+loginUser);
		if(loginUser==null) {
			System.out.println("loginUser: "+loginUser);
			return "redirect:/cookie/login";
		}
		Page<User> userList = this.userService.userList(page);
		model.addAttribute("userList", userList); //model 객체로 userList 전달
	
		return "template/user/userList.html";
	}
	
	
	//사용자정보 수정 페이지
	@GetMapping("/userUpdate.do/{idx}")
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
	//사용자 수정 처리
	@PostMapping("/userUpdate.do/{idx}")
	public String UserUpdate(UserUpdateDTO userUpdateDTO,@RequestParam("newPw") String newPw) {
		//this.userService.changeNewUserPw(userUpdateDTO, newPw);	
		String res =this.userService.update(userUpdateDTO,newPw);
		return "redirect:"+res;
	}
	
	//수정 실패시
	@GetMapping("/user/update-fail/{idx}")
	public String updateFail(Model model,@PathVariable("idx") Integer idx) {
		model.addAttribute("idx",idx);
		return "template/user/update-fail";
	}
	
	//사용자 삭제 Ui
	@GetMapping("/userDelete.do/{idx}")
	public String userDeleteUi(@PathVariable("idx") Integer idx, Model model) {
		//Model에 idx 적용
		model.addAttribute("idx",idx);
		
		return "template/user/userDelete.html";
	}
	
	
	//사용자 삭제처리
	@PostMapping("/userDelete.do/{idx}")
	public String userDelete(@PathVariable("idx") int idx,@RequestParam("pw") String userPw) {
		String res = this.userService.delete(idx,userPw); //사용자 삭제
		
		return "redirect:"+res;
	}

	//삭제 실패시
	@GetMapping("/delete-fail/{idx}")
	public String deleteFail(Model model,@PathVariable("idx") Integer idx) {
		model.addAttribute("idx",idx);
		return "template/user/delete-fail";
	}
	

	//사용자 검색
	@GetMapping("/search")
	public String searchList(Model model, @RequestParam("keyword") String keyword,@RequestParam(value="page",defaultValue="0")int page) {
		Page<User> userList = this.userService.searchList(keyword, page); // Service 객체의 searchList() 호출
		model.addAttribute("keyword",keyword); //검색키워드
		model.addAttribute("userList",userList);
		return "template/user/searchList";
	}
	
	@GetMapping("/test")
	public String test() {
		return "template/test";
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
