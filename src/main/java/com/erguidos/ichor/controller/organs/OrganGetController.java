package com.erguidos.ichor.controller.organs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.OrganTypeInfoResponse;
import com.erguidos.ichor.service.organ.OrganServiceInterface;

@RestController
@RequestMapping("/api/v1/organs")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class OrganGetController {
	
	private final OrganServiceInterface organService;
	
	OrganGetController(OrganServiceInterface organService) {
		this.organService = organService;
	}
	
	@GetMapping("/type-info")
	public ResponseEntity<List<OrganTypeInfoResponse>> getOrganTypeInfo(){
		return ResponseEntity.ok(organService.getOrganTypeInfo());
	}
	
	
}
