package com.erguidos.ichor.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor extends WorksInHospital {
	
	@OneToMany(mappedBy = "doctor")
	private List<OrganPetition> organPetitions;
    
    protected Doctor() {}

	public List<OrganPetition> getOrganPetitions() { return organPetitions; }
}
