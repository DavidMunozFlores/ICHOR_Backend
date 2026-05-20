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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient  {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "internal_id", nullable = false, length = 255)
	private String internalID;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@Column(name = "identification", unique = true, nullable = false, length = 255)
	private String identification;
	
	@Column(name = "blood_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;
	
	@Column(name = "height", nullable = false)
	private Double height;
	
	@Column(name = "weight", nullable = false)
	private Double weight;
	
	@ManyToOne
	@JoinColumn(name = "id_hospital")
	private Hospital hospital;
	
	@OneToMany(mappedBy = "patient")
	private List<OrganPetition> organPetitions;
	
	protected Patient() {}
    
	public Long getId() { return this.id; }

	public String getInternalID() { return internalID; }

	public String getName() { return name; }

	public String getIdentification() { return identification; }

	public BloodType getBloodType() { return bloodType; }

	public Double getHeight() { return height; }

	public Double getWeight() { return weight; }

	public Hospital getHospital() { return hospital; }

	public List<OrganPetition> getOrganPetitions() { return organPetitions; }
}
