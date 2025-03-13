package com.example.demo.service;


import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FacilityCreateDTO;
import com.example.demo.dto.FacilityUpdateDTO;
import com.example.demo.entity.Facility;
import com.example.demo.entity.FacilityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityService {
	
	private final FacilityRepository repositroy;

	
	//시설물 생성
	public void save(FacilityCreateDTO facilityCreateDTO) {
		
		this.repositroy.save(facilityCreateDTO.toEntity());
	}

	//시설물 리스트 페이지 가져오기
	public Page<Facility> facilityList(int page) {
		if(page==0) {
			page = 0;
		}else {
			page-=1;
		}
		Sort sort =Sort.by(Order.asc("idx")); //정렬기준 오름차순 
		Pageable pageable = PageRequest.of(page, 5,sort);
		return this.repositroy.findAll(pageable);		
	}
	
	//시설물 수정할 정보 가져오기
	public FacilityUpdateDTO updateUi(Integer idx) throws NoSuchElementException {
		Facility facility = this.repositroy.findById(idx).orElseThrow();
		return FacilityUpdateDTO.makeFacility(facility);
	}	
	
	//시설물 정보 수정처리
	public void update(FacilityUpdateDTO updateDTO) throws NoSuchElementException{
		Facility facility = this.repositroy.findById(updateDTO.getIdx()).orElseThrow();
		updateDTO.changeFacility(facility);
		this.repositroy.save(facility);
	}
	
	//시설물 정보 삭제처리
	public void delete(Integer idx) throws NoSuchElementException{
		Facility facility= repositroy.findById(idx).orElseThrow();
		repositroy.delete(facility);
	}

}
