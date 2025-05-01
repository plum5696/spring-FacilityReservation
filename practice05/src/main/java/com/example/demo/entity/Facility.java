package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Facility {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;
	
	@Column(length=255)
	private String name; //시설물명
	
	@Column(length=255)
	private String content; //시설물 내용
	
	@Column(length=255)
	private String phone; //시설물 전화번호

	@Column(length=255)
	private String address; //시설물 주소
	
	//Entity 빌더
	@Builder
	public Facility(String name, String content, String phone,
			String address) {
		this.name = name;
		this.content = content;
		this.phone = phone;
		this.address =address;
	}

}
