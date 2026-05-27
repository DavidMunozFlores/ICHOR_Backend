package com.erguidos.ichor.controller.doctors;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.DoctorResponse;
import com.erguidos.ichor.service.doctor.DoctorServiceInterface;

@RestController
@RequestMapping("/api/v1/doctors")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorGetController {
	
	private final DoctorServiceInterface doctorService;
	
	DoctorGetController(DoctorServiceInterface doctorService) {
		this.doctorService = doctorService;
	}
	
	@GetMapping
	public ResponseEntity<List<DoctorResponse>> getAllDoctors(){
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id){
		return ResponseEntity.ok(doctorService.getDoctorById(id));
	}

}
