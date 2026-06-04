package com.erguidos.ichor.controller.organ_petitions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.StateUpdateOrganPetitionRequest;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.OrganPetitionRequest;
import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.organ_petition.OrganPetitionServiceInterface;

@RestController
@RequestMapping("/api/v1/organ-petitions")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class OrganPetitionCreateController {
	private final OrganPetitionServiceInterface organPetitionService;
	private final AuthServiceInterface authService;
	
	OrganPetitionCreateController(
			OrganPetitionServiceInterface organPetitionService,
			AuthServiceInterface authService
			) {
		this.organPetitionService = organPetitionService;
		this.authService = authService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<OrganPetitionResponse> createOrganPetition(
			@RequestBody AuthenticatedRequest<OrganPetitionRequest> ar
			) {
		
        this.authService.authenticate(ar.authCredentials(), Role.DOCTOR);
		
		return ResponseEntity.ok(organPetitionService.createOrganPetition(ar));
	}
	
	@PatchMapping("/accept")
	public ResponseEntity<OrganPetitionResponse> acceptOrganPetition(
			@RequestBody AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar
			) {
		
        this.authService.authenticate(ar.authCredentials(), Role.DOCTOR);
		
		return ResponseEntity.ok(organPetitionService.acceptOrganPetition(ar));
	}
	
	@PatchMapping("/cancell")
	public ResponseEntity<OrganPetitionResponse> cancellOrganPetition(
			@RequestBody AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar
			) {
		
		this.authService.authenticate(ar.authCredentials(), Role.DOCTOR);
		
		return ResponseEntity.ok(organPetitionService.cancellOrganPetition(ar));
	}
	
	@PatchMapping("/check")
	public ResponseEntity<OrganPetitionResponse> checkOrganPetition(
			@RequestBody AuthenticatedRequest<StateUpdateOrganPetitionRequest> ar
			) {
		
		this.authService.authenticate(ar.authCredentials(), Role.DOCTOR);
		
		return ResponseEntity.ok(organPetitionService.checkOrganPetition(ar));
	}
}
