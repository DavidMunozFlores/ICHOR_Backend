package com.erguidos.ichor.entity;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
 
@Entity
@Table(name="organs")
public class Organ extends BaseEntity {
	
	@Column(name = "organ_type")
	@Enumerated(EnumType.STRING)
	private OrganType organType;
	
	@Column(name="weight_grams")
	private Double weightGrams;
	
	@Column(name="volume_c_c")
	private Double volumeCC;
	
	/**
	 * Chain must be a six ordered hla markers to match compatibility
	 */
	@Column(name="hla_chain")
	private String hlaChain;
	
	@Column(name="")
	private Hospital donorHospital;
	
	@Column(name="assigned")
	private boolean assigned;
	
	protected Organ() {}

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

	public Hospital getDonorHospital() {
		return donorHospital;
	}

	public boolean isAssigned() {
		return assigned;
	}
	
	
}