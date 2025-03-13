package com.example.demo.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.FacilityCreateDTO;
import com.example.demo.dto.FacilityUpdateDTO;
import com.example.demo.entity.Facility;
import com.example.demo.service.FacilityService;

@Controller
public class FacilityController {
	
	@Autowired
	FacilityService service;
	
	//시설물 등록
	@GetMapping("/facility/create")
	public String FacilityCreateUi() {
		return "template/facility/facilityCreate.html";
	}
	
	//시설물 등록처리
	@PostMapping("/facility/create")
	public String FacilityCreate(FacilityCreateDTO facilityCreateDTO) {
		this.service.save(facilityCreateDTO);
		return "redirect:/facility/list";
	}
	
	//시설물 리스트
	@GetMapping("/facility/list")
	public String FacilityList(Model model,@RequestParam(value="page", defaultValue="0") int page) {
		Page<Facility> facilites =this.service.facilityList(page);
		model.addAttribute("facilityList",facilites);
		return "template/facility/facilityList.html";
	}
	
	//시설물 정보수정 ui
	@GetMapping("/facility/update/{idx}")
	public ModelAndView FacilityUpdateUi(@PathVariable("idx") Integer idx) {
		ModelAndView mav = new ModelAndView();
		try {
			FacilityUpdateDTO facilityUpdateDTO = new FacilityUpdateDTO();
			facilityUpdateDTO = this.service.updateUi(idx);
			mav.addObject("facilityUpdateDTO", facilityUpdateDTO);
			mav.setViewName("template/facility/facilityUpdate.html");
		}catch(NoSuchElementException ex) {
			ex.printStackTrace();
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.setViewName("redirect:/facility/list");
		}
		return mav;
	}
	
	//시설물 정보수정 처리
	@PostMapping("/facility/update/{idx}")
	public String FacilityUpdate(FacilityUpdateDTO updateDTO) {
		this.service.update(updateDTO);
		return "redirect:/facility/list";
	}
	
	//시설물 삭제처리
	@GetMapping("/facility/delete/{idx}")
	public String FacilityDelete(@PathVariable("idx")Integer idx) {
		this.service.delete(idx);
		return "redirect:/facility/list";
	}
	
}
