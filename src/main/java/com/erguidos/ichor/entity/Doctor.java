package com.erguidos.ichor.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor extends WorksInHospital {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@OneToMany(mappedBy = "doctor")
	private List<OrganPetition> organPetitions;
    
    protected Doctor() {}

	public Long getId() {
		return id;
	}

	public List<OrganPetition> getOrganPetitions() {
		return organPetitions;
	}
    
    
}
