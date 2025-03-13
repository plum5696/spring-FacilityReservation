package com.example.demo.dto;

import com.example.demo.entity.Facility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityCreateDTO {

	private String name; //시설물명
	private String content; //시설물 소개내용
	private String phone; //시설물 전화번호
	private String address; //시설물 주소
	
	
	//Entity 객체로 반환
	public Facility toEntity() {
		return Facility.builder().name(name)
				.content(content)
				.phone(phone)
				.address(address)
				.build();
	}
	
}
