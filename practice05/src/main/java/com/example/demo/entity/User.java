package com.example.demo.entity;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="users")
@Entity
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User {
	//Field
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idx; //사용자 식별번호(Id)
	
	@Column(length=30,nullable=false)
	private String id; //아이디

	@Column(length=60,nullable=false)
	private String pw; //비밀번호
	
	@Column(length=255)
	private String name; //성명

	@Column(length=255)
	private String email; //이메일
	
	@Column
	private String gender; //성별
	
	@Column(length=255)
	private String phone; //전화번호
	
	@Column(length=100)
	private String birthdate; //생년월일

	//Entity 빌더
	@Builder
	public User(String id, String pw, String name, 
			String email,String gender,String phone,
			String birthdate) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.phone = phone;
		this.birthdate = birthdate;
	}
	
	

	


	

	
	
	
}
