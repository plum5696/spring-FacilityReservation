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
public class UserCreateDTO {
	private String id;
	private String pw;
	private String name;
	private String email;
	private String gender;
	private String phone;
	private String birthdate;
	

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
