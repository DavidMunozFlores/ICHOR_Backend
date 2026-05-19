package com.erguidos.ichor.entity;

import java.util.List;

import com.erguidos.ichor.enums.BloodType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
	@Column(name = "internal_id")
	private String internalID;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "identification")
	private String identification;
	
	@Column(name = "blood_type")
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;
	
	@Column(name = "height")
	private Double height;
	
	@Column(name = "weight")
	private Double weight;
	
	@OneToMany(mappedBy = "patient")
	private List<OrganPetition> organPetitions;
	
	protected Patient() {}
    
	public Long getId() { return this.id; }

	public String getInternalID() {
		return internalID;
	}

	public String getName() {
		return name;
	}

	public String getIdentification() {
		return identification;
	}

	public BloodType getBloodType() {
		return bloodType;
	}
	
	
}
