package com.example.demo.dto;

import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
	private Integer idx;
	private String id;
	private String pw;
	private String name;
	private String email;
	private String gender;
	private String phone;
	private String birthdate;
	
	//from User setting fields
	public UserUpdateDTO fromUser(User user) {
		this.idx = user.getIdx();
		this.id =user.getId();
		this.pw =user.getPw();
		this.name = user.getEmail();
		this.email = user.getEmail();
		this.gender = user.getGender();
		this.phone = user.getPhone();
		this.birthdate = user.getBirthdate();
		return this;
	}
	
	//User객체를 DTO 객체로 변환 
	public static UserUpdateDTO makeUser(User user) {
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO(); //UserUpdateDTO 객체생성
		userUpdateDTO.fromUser(user); //fromUser 메서드로 user객체 필드값 가져오기
		return userUpdateDTO;
	}
	
	//Update 내용 반영 User객체 생성
	public User changeUser(User user) {
		user.setId(id);
		user.setPw(pw);
		user.setName(name);
		user.setEmail(email);
		user.setGender(gender);
		user.setPhone(phone);
		user.setBirthdate(birthdate);
		return user;
	}

	public User toEntity() {
		return User.builder()
				.id(id)
				.pw(pw)
				.name(name)
				.email(email)
				.gender(gender)
				.phone(phone)
				.birthdate(birthdate)
				.build();
	}
	//toString()
	@Override
	public String toString() {
		String str=String.format("아이디: %s 비밀번호: %s 성명:%s 이메일:%s 성별:%s 전화번호:%s 생년월일:%s",
			this.id,this.pw,this.name,this.email,this.gender,this.phone,this.birthdate);
		return str;
	}
}
