package com.erguidos.ichor.controller.organ_petitions;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.organ_petition.OrganPetitionServiceInterface;

@RestController
@RequestMapping("/api/v1/organ-petitions")
@CrossOrigin(origins = {"${app.cors.allowed}"})
public class OrganPetitionGetController {
	private final OrganPetitionServiceInterface organPetitionService;
	
	OrganPetitionGetController(
			OrganPetitionServiceInterface organPetitionService,
			AuthServiceInterface authService
			){
		this.organPetitionService = organPetitionService;
	}
	
    @GetMapping("/state/{state}")
    public ResponseEntity<Set<OrganPetitionResponse>> findByOrganPetitionState(@PathVariable OrganPetitionState state) {
        return ResponseEntity.ok(this.organPetitionService.findByOrganPetitionState(state));
    }
    
    @GetMapping("/populate")
    public ResponseEntity<Set<OrganPetitionResponse>> generateOPs() {
        return ResponseEntity.ok(this.organPetitionService.generateOPs());
    }
}
