package com.erguidos.ichor.controller.organs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.annotations.AuthenticatedRequestPayload;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.RegisterOrganRequest;
import com.erguidos.ichor.dto.response.RegisterOrganResponse;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.organ.OrganServiceInterface;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/organs")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class OrganRegisterController {

	private final OrganServiceInterface organService;
	
	OrganRegisterController(OrganServiceInterface organService) {
		this.organService = organService;
	}
	
	@PostMapping("/register-organ")
	public ResponseEntity<RegisterOrganResponse> registerOrgan(
		@RequestBody AuthenticatedRequest<RegisterOrganRequest> ar
	) {
		return ResponseEntity.ok(organService.registerOrgan(ar));
	}
}
