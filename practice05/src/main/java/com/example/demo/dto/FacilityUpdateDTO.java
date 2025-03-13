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
public class FacilityUpdateDTO {
	
	private Integer idx; //번호
	private String name; //시설물명
	private String content; //시설물 소개내용
	private String phone; //시설물 전화번호
	private String address; //시설물 주소
	
	//Entity 필드 가져오기
	public FacilityUpdateDTO fromFacility(Facility facility) {
		this.idx =facility.getIdx();
		this.name = facility.getName();
		this.content = facility.getContent();
		this.phone = facility.getPhone();
		this.address = facility.getAddress();
		return this;
	}
	
	//Facility객체에서 DTO객체로
	public static FacilityUpdateDTO makeFacility(Facility facility) {
		FacilityUpdateDTO facilityDTO = new FacilityUpdateDTO();
		facilityDTO.fromFacility(facility);
		return facilityDTO;
	}
	
	//수정DTO 정보반영
	public FacilityUpdateDTO changeFacility(Facility facility) {
		facility.setName(name);
		facility.setContent(content);
		facility.setPhone(phone);
		facility.setAddress(address);
		return this;
	}
}
