package com.example.demo.service;


import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

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
		return userRepository.save(user.toEntity());
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
	public void update(UserUpdateDTO userUpdateDTO) throws NoSuchElementException {
		User user = this.userRepository.findById(userUpdateDTO.getIdx()).orElseThrow();
		userUpdateDTO.changeUser(user);
		this.userRepository.save(user); //수정 처리 반영		
	}
	
	//사용자삭제
	public String delete(int idx,String userPw) throws NoSuchElementException{
		User user = this.userRepository.findById(idx).orElseThrow();
		if(!userPw.equals(user.getPw())) { //비밀번호 불일치 시
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
}
