package com.erguidos.ichor.controller.doctors;

import java.security.GeneralSecurityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.response.WorkerCreatedResponse;
import com.erguidos.ichor.service.doctor.DoctorServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;


@RestController
@RequestMapping("/api/v1/doctor")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {
	
	private final DoctorServiceInterface doctorService;
	
	DoctorController(DoctorServiceInterface doctorService) {
		this.doctorService = doctorService;
	}

	@PostMapping("/create")
	public ResponseEntity<WorkerCreatedResponse> createDoctor(@RequestBody DecryptRequest decryptDTO)
			throws JsonProcessingException, GeneralSecurityException {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(decryptDTO));
	}
}
