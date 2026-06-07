package com.erguidos.ichor.controller.organ_petitions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.IdOrganPetitionRequest;
import com.erguidos.ichor.annotations.AuthenticatedRequestPayload;
import com.erguidos.ichor.annotations.AuthenticatedPayload;
import com.erguidos.ichor.dto.request.OrganPetitionRequest;
import com.erguidos.ichor.dto.request.OrganPetitionUpdateRequest;
import com.erguidos.ichor.dto.response.OPDeleted;
import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.service.organ_petition.OrganPetitionServiceInterface;

@RestController
@RequestMapping("/api/v1/organ-petitions")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class OrganPetitionCreateController {
	private final OrganPetitionServiceInterface organPetitionService;
	
	OrganPetitionCreateController(OrganPetitionServiceInterface organPetitionService) {
		this.organPetitionService = organPetitionService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<OrganPetitionResponse> createOrganPetition(
		@RequestBody @AuthenticatedRequestPayload(Role.DOCTOR) AuthenticatedRequest<OrganPetitionRequest> ar
	) {
		return ResponseEntity.ok(organPetitionService.createOrganPetition(ar));
	}
	
	@PostMapping("/update")
	public ResponseEntity<OrganPetitionResponse> updateOrganPetition(
		@RequestBody @AuthenticatedRequestPayload(Role.DOCTOR) AuthenticatedRequest<OrganPetitionUpdateRequest> ar
	) {
		return ResponseEntity.ok(organPetitionService.updateOrganPetition(ar));
	}
	
	@PostMapping("/delete")
	public ResponseEntity<OPDeleted> deleteOrganPetition(
		@RequestBody @AuthenticatedPayload(Role.DOCTOR) IdOrganPetitionRequest idOrganPetitionRequest
	) {
		return ResponseEntity.ok(organPetitionService.deleteOrganPetition(idOrganPetitionRequest));
	}
	
	@PatchMapping("/accept")
	public ResponseEntity<OrganPetitionResponse> acceptOrganPetition(
		@RequestBody @AuthenticatedPayload(Role.DOCTOR) IdOrganPetitionRequest idOrganPetitionRequest
	) {
		return ResponseEntity.ok(organPetitionService.acceptOrganPetition(idOrganPetitionRequest));
	}
	
	@PatchMapping("/cancel")
	public ResponseEntity<OrganPetitionResponse> cancellOrganPetition(
		@RequestBody @AuthenticatedPayload(Role.DOCTOR) IdOrganPetitionRequest idOrganPetitionRequest
    ) {
		return ResponseEntity.ok(organPetitionService.cancelOrganPetition(idOrganPetitionRequest));
	}
	
	@PatchMapping("/check")
	public ResponseEntity<OrganPetitionResponse> checkOrganPetition(
		@RequestBody @AuthenticatedPayload(Role.DOCTOR) IdOrganPetitionRequest idOrganPetitionRequest
	) {
		return ResponseEntity.ok(organPetitionService.checkOrganPetition(idOrganPetitionRequest));
	}
}
