package com.example.demo.service;


import java.util.NoSuchElementException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	//사용자 등록
	public User save(UserCreateDTO user) {
		return userRepository.save(encryptPassword(user.toEntity())); //비밀번호 암호화뒤에 사용자저장
	}

	//사용자 목록페이지 
	public Page<User> userList(int page) {
		if(page==0) {
			page=0; 
		}else {
			page-=1;//기본페이지는 0부터 시작하기에 -1
		}
		Pageable pageable = PageRequest.of(page, 10); //페이지당 유저수
		
		return userRepository.findAll(pageable);
	}
	
	//수정 사용자 정보 가져오기
	public UserUpdateDTO updateUi(Integer idx) throws NoSuchElementException{
		User user =this.userRepository.findById(idx).orElseThrow();
		
		return UserUpdateDTO.makeUser(user);
	}

	//사용자정보 수정
	public String update(UserUpdateDTO userUpdateDTO,String newPw) throws NoSuchElementException {
		User user = this.userRepository.findById(userUpdateDTO.getIdx()).orElseThrow();
		

		System.out.println("user: "+user.getPw()+" | userUpdateDTO : "+ userUpdateDTO.getPw());
		
		//암호화여부 체크
		if(!user.getPw().startsWith("$2a$") && !user.getPw().startsWith("$2b$")) {
			throw new IllegalStateException("저장된 비밀번호가 암호화 되지 않았습니다. DB에서 점검해주세요");
		}
		
		if(!BCrypt.checkpw(userUpdateDTO.getPw(), user.getPw())) { // 사용자 비번 불일치시
			System.out.println(userUpdateDTO.getPw()+" and "+user.getPw());
			return "/user/update-fail/"+userUpdateDTO.getIdx();
		}else {
			
		//새로운 비밀번호 입력시 변경
		if(StringUtils.hasText(newPw)) {
			System.out.println("새 비번으로 변경 ");
			System.out.println(userUpdateDTO+" , "+newPw);
			userUpdateDTO.setPw(newPw); // 새 비밀번호로 변경
		}
		
		userUpdateDTO.changeUser(user);
		
		//비밀번호가 평문이면 암호화
		if(StringUtils.hasText(user.getPw()) && !user.getPw().startsWith("$2a$")) {
			encryptPassword(user);
		}
		
		this.userRepository.save(user); //수정 처리 반영
		return "/user/userList.do";
		}
	}
	
	//사용자삭제
	public String delete(int idx,String userPw) throws NoSuchElementException{
		User user = this.userRepository.findById(idx).orElseThrow();
		if(!BCrypt.checkpw(userPw, user.getPw())) { //비밀번호 불일치 시 (암호화된 비번과 비교)
			return "/user/delete-fail/"+idx;
		}else {
			this.userRepository.delete(user); //삭제처리
			return "/user/userList.do";
		}
		
	}
	
	//사용자검색
	public Page<User> searchList(String keyword, int page) {
		if(page==0) {
			page=0; 
		}else {
			page-=1;//기본페이지는 0부터 시작하기에 -1
		}
		Sort sort =Sort.by(Order.desc("idx"));
		Pageable pageable = PageRequest.of(page, 10,sort); //페이지당 유저수
		return userRepository.findByNameContaining(keyword,pageable);
	}

	//ID중복 체크
	public boolean checkIdDuplication(String id) {
		boolean IdCheckValue= this.userRepository.existsById(id);
		return IdCheckValue;
	}
	
	//사용자비밀번호 암호화
	public User encryptPassword(User user) {
		if(user.getPw() != null && !(user.getPw().startsWith("$2a$") || user.getPw().startsWith("$2b$"))) {
			String encryptPassword = BCrypt.hashpw(user.getPw(), BCrypt.gensalt());
			user.setPw(encryptPassword);
		}		
		return user;
	}

	//사용자 암호화된 비밀번호 일치 여부 체크
	public Boolean checkUserPw(String plainPw, String encryptPw) throws NoSuchElementException{
		return BCrypt.checkpw(plainPw, encryptPw);
	}
	
	//새로운 비밀번호 입력시 변경
	public void changeNewUserPw(UserUpdateDTO userUpdateDTO, String newPw) {
		
		if(StringUtils.hasText(newPw)) {
			System.out.println("새 비번으로 변경 ");
			System.out.println(userUpdateDTO+" , "+newPw);
			userUpdateDTO.setPw(newPw); // 새 비밀번호로 변경
		}
	}
}
