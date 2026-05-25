package com.erguidos.ichor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.CreateUserRequestDTO;
import com.erguidos.ichor.dto.request.CreateUserRequestDTOV2;
import com.erguidos.ichor.service.DoctorService;


@RestController
@RequestMapping("/api/v1/doctor")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {
	
	private DoctorService doctorService;
	
	DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createDoctor(@RequestBody CreateUserRequestDTO doctorRequestDTO){
		doctorService.createDoctor(doctorRequestDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("DOCTOR CREATED");
	}
	
	@PostMapping("/create2")
	public ResponseEntity<String> createDoctor(@RequestBody CreateUserRequestDTOV2 doctorRequestDTO){
		doctorService.createDoctorV2(doctorRequestDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("DOCTOR CREATED");
	}
}
