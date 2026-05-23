package com.erguidos.ichor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.DoctorRequestDTO;
import com.erguidos.ichor.service.DoctorService;


@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
	
	private DoctorService doctorService;
	
	DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO){
		doctorService.createDoctor(doctorRequestDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("DOCTOR CREATED");
	}
}
