package com.erguidos.ichor.entity;

import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.enums.OrganType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "organ_petitions")
public class OrganPetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
	@Column(name = "organ_type")
	@Enumerated(EnumType.STRING)
	private OrganType organType;
	
	@Column(name="weight_grams")
	private Double weightGrams;
	
	@Column(name="volume_c_c")
	private Double volumeCC;
	
	@Column(name="hla_chain")
	private String hlaChain;
	
	@ManyToOne
	@JoinColumn(name = "id_doctor")
	private Doctor doctor;
	
	@Column(name="")
	private Patient patient;
	
	@Column(name = "organ_petition_state")
	private OrganPetitionState organPetitionState;
	
	@Column(name = "")
	private Organ organ;
	
	protected OrganPetition() {}
    
	public Long getId() { return this.id; }

	public OrganType getOrganType() {
		return organType;
	}

	public Double getWeightGrams() {
		return weightGrams;
	}

	public Double getVolumeCC() {
		return volumeCC;
	}

	public String getHlaChain() {
		return hlaChain;
	}

	public Patient getPatient() {
		return patient;
	}

	public OrganPetitionState getOrganPetitionState() {
		return organPetitionState;
	}

	public Organ getOrgan() {
		return organ;
	}
	
	
	
}
